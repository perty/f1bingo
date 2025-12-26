package se.artcomputer.f1.bingo.controller.auth;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.entity.AuthSession;
import se.artcomputer.f1.bingo.entity.Fan;
import se.artcomputer.f1.bingo.repository.AuthSessionRepository;
import se.artcomputer.f1.bingo.repository.FanRepository;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;


@Service
public class SessionService {
    private final AuthSessionRepository sessions;
    private final FanRepository users;
    private final SecureRandom random = new SecureRandom();


    public SessionService(AuthSessionRepository sessions, FanRepository users) {
        this.sessions = sessions;
        this.users = users;
    }

    public AuthSession createFor(Long userId) {
        AuthSession authSession = new AuthSession();
        authSession.setId(base64Url(randomBytes(24)));
        authSession.setFanId(userId);
        authSession.setExpiresAt(Instant.now().plus(Duration.ofMinutes(30))); // 30 min
        return sessions.save(authSession);
    }

    public boolean isValid(String sid) {
        return sessions.findById(sid)
                .filter(s -> s.getExpiresAt().isAfter(Instant.now()))
                .isPresent();
    }


    public Optional<Fan> userFor(String sid) {
        return sessions.findById(sid).flatMap(s -> users.findById(s.getFanId()));
    }


    public void delete(String sid) {
        sessions.deleteById(sid);
    }


    private byte[] randomBytes(int n) {
        byte[] b = new byte[n];
        random.nextBytes(b);
        return b;
    }

    private String base64Url(byte[] b) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(b);
    }
}
