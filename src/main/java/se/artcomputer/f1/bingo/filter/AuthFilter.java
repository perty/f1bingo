package se.artcomputer.f1.bingo.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import se.artcomputer.f1.bingo.controller.auth.RememberMeService;
import se.artcomputer.f1.bingo.controller.auth.SessionService;
import se.artcomputer.f1.bingo.controller.auth.UserContext;
import se.artcomputer.f1.bingo.entity.AuthSession;
import se.artcomputer.f1.bingo.entity.Fan;
import se.artcomputer.f1.bingo.entity.RememberMeToken;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static se.artcomputer.f1.bingo.controller.auth.AuthConstants.REMEMBER_ME_COOKIE;
import static se.artcomputer.f1.bingo.controller.auth.AuthConstants.SESSION_COOKIE;

@Component
@Order(10)
@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {

    private final SessionService sessions;
    private final RememberMeService rememberMe;

    public AuthFilter(SessionService sessions, RememberMeService rememberMe) {
        this.sessions = sessions;
        this.rememberMe = rememberMe;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) req;
        HttpServletResponse servletResponse = (HttpServletResponse) res;

        if (isPublic(chain, servletRequest, servletResponse) ||
                sessionIsValied(chain, servletRequest, servletResponse) ||
                rememberMeIsValid(chain, servletRequest, servletResponse)) {
            return;
        }

        redirectToLogin(servletRequest, servletResponse);
    }

    private static void redirectToLogin(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException {
        String next = servletRequest.getRequestURI() + (servletRequest.getQueryString() != null ? "?" + servletRequest.getQueryString() : "");
        String enc = java.net.URLEncoder.encode(next, StandardCharsets.UTF_8);
        servletResponse.sendRedirect("/f1login-form.html?next=" + enc);
    }

    private boolean rememberMeIsValid(FilterChain chain, HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException, ServletException {
        String rm = getCookieFromRequest(servletRequest, REMEMBER_ME_COOKIE);
        if (rm != null) {
            Optional<RememberMeService.AutoLogin> auto = rememberMe.tryAutoLogin(rm);
            if (auto.isPresent()) {
                Fan user = auto.get().user();
                RememberMeToken rec = auto.get().record();
                AuthSession authSession = sessions.createFor(user.getId());
                addSecureCookie(servletResponse, SESSION_COOKIE, authSession.getId(), 1800);
                String newValue = rememberMe.rotate(rec);
                addSecureCookie(servletResponse, REMEMBER_ME_COOKIE, newValue, 60 * 60 * 24 * 30);
                UserContext.set(user);
                chain.doFilter(servletRequest, servletResponse);
                return true;
            }
        }
        return false;
    }

    private boolean sessionIsValied(FilterChain chain, HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException, ServletException {
        String sid = getCookieFromRequest(servletRequest, SESSION_COOKIE);
        if (sid != null && sessions.isValid(sid)) {
            sessions.userFor(sid).ifPresent(UserContext::set);
            chain.doFilter(servletRequest, servletResponse);
            return true;
        }
        return false;
    }

    private boolean isPublic(FilterChain chain, final HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        final String path = servletRequest.getRequestURI();
        if (path.startsWith("/chat-ws") ||
                path.startsWith("/points/results") ||
                path.startsWith("/home.html") ||
                path.startsWith("/index.html") ||
                path.startsWith("/login") ||
                path.startsWith("/manifest.json") ||
                path.startsWith("/service-worker.js") ||
                path.startsWith("/f1login-form.html") ||
                path.startsWith("/favicon.ico") ||
                path.startsWith("/public")) {
            chain.doFilter(servletRequest, servletResponse);
            return true;
        }
        return false;
    }


    private String getCookieFromRequest(HttpServletRequest servletRequest, String name) {
        if (servletRequest.getCookies() == null) return null;
        for (Cookie c : servletRequest.getCookies()) if (name.equals(c.getName())) return c.getValue();
        return null;
    }


    private void addSecureCookie(HttpServletResponse w, String name, String value, int maxAgeSeconds) {
        Cookie c = new Cookie(name, value);
        c.setHttpOnly(true);
        c.setSecure(true);
        c.setPath("/");
        c.setMaxAge(maxAgeSeconds);
// SameSite kan sättas via header om du vill: w.addHeader("Set-Cookie", name + "=" + value + "; Path=/; Max-Age="+maxAgeSeconds+"; HttpOnly; Secure; SameSite=Lax");
        w.addCookie(c);
    }

}
