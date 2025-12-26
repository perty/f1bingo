package se.artcomputer.f1.bingo.controller.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import se.artcomputer.f1.bingo.entity.AuthSession;
import se.artcomputer.f1.bingo.entity.Fan;
import se.artcomputer.f1.bingo.repository.FanRepository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

import static se.artcomputer.f1.bingo.controller.auth.AuthConstants.REMEMBER_ME_COOKIE;
import static se.artcomputer.f1.bingo.controller.auth.AuthConstants.SESSION_COOKIE;

@RestController
@RequestMapping
public class AuthController {

    private final FanRepository users;
    private final SessionService sessions;
    private final RememberMeService rememberMeService;

    public AuthController(FanRepository users, SessionService sessions, RememberMeService rememberMeService) {
        this.users = users;
        this.sessions = sessions;
        this.rememberMeService = rememberMeService;
    }

    public record LoginDto(String name, String password, boolean rememberMe) {
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void login(@RequestParam String username,
                      @RequestParam String password,
                      @RequestParam(defaultValue = "false", name = "remember-me") boolean rememberMeParameter,
                      @RequestParam(required = false) String next,
                      HttpServletRequest r,
                      HttpServletResponse servletResponse) throws IOException {
        LoginDto dto = new LoginDto(username, password, rememberMeParameter);
        Optional<Fan> optionalFan = users.findByName(dto.name());

        if (optionalFan.isEmpty() || !BCrypt.checkpw(dto.password(), optionalFan.get().getPassword())) {
            servletResponse.sendRedirect("/login?error=1");
            return;
        }

        AuthSession s = sessions.createFor(optionalFan.get().getId());
        addSecureCookie(servletResponse, SESSION_COOKIE, s.getId(), 1800);

        if (dto.rememberMe()) {
            String cookieValue = rememberMeService.createCookieValue(optionalFan.get().getId());
            addSecureCookie(servletResponse, REMEMBER_ME_COOKIE, cookieValue, 60 * 60 * 24 * 30);
        }
        servletResponse.sendRedirect(next != null ? next : "/");
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(value = SESSION_COOKIE, required = false) String sid, HttpServletResponse servletResponse) throws URISyntaxException {
        if (sid != null) sessions.delete(sid);
        expire(servletResponse, SESSION_COOKIE);
        expire(servletResponse, REMEMBER_ME_COOKIE);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI("/"));
        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        Optional<Fan> optionalFan = UserContext.get();
        if (optionalFan.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Fan fan = optionalFan.get();
        return ResponseEntity.ok(Map.of("id", fan.getId(), "name", fan.getName()));
    }

    private void addSecureCookie(HttpServletResponse w, String name, String value, int maxAgeSeconds) {
        Cookie c = new Cookie(name, value);
        c.setHttpOnly(true);
        c.setSecure(true);
        c.setPath("/");
        c.setMaxAge(maxAgeSeconds);
        w.addCookie(c);
    }

    private void expire(HttpServletResponse w, String name) {
        Cookie c = new Cookie(name, "");
        c.setHttpOnly(true);
        c.setSecure(true);
        c.setPath("/");
        c.setMaxAge(0);
        w.addCookie(c);
    }
}
