package se.artcomputer.f1.bingo.controller.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class WsHandshakeAuthInterceptor implements HandshakeInterceptor {

    private final SessionService sessions;
    private final RememberMeService rememberMe;

    public WsHandshakeAuthInterceptor(SessionService sessions, RememberMeService rememberMe) {
        this.sessions = sessions;
        this.rememberMe = rememberMe;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {

        if (!(request instanceof ServletServerHttpRequest sreq)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }

        HttpServletRequest r = sreq.getServletRequest();

        // 1) sid
        String sid = cookie(r, "sid");
        if (sid != null && sessions.isValid(sid)) {
            return sessions.userFor(sid)
                    .map(u -> {
                        attributes.put("userId", u.getId());
                        return true;
                    })
                    .orElseGet(() -> {
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                        return false;
                    });
        }

        // 2) remember-me (OBS: här kan du autentisera men INTE enkelt rotera cookie utan HttpServletResponse)
        String rm = cookie(r, "rm");
        if (rm != null) {
            var auto = rememberMe.tryAutoLogin(rm);
            if (auto.isPresent()) {
                attributes.put("userId", auto.get().user().getId());
                return true;
            }
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }

    private String cookie(HttpServletRequest r, String name) {
        if (r.getCookies() == null) return null;
        for (Cookie c : r.getCookies()) if (name.equals(c.getName())) return c.getValue();
        return null;
    }
}
