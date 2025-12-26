package se.artcomputer.f1.bingo.controller.auth;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.entity.Fan;
import se.artcomputer.f1.bingo.entity.RememberMeToken;
import se.artcomputer.f1.bingo.repository.FanRepository;
import se.artcomputer.f1.bingo.repository.RememberMeRepository;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;


@Service
public class RememberMeService {
    private final RememberMeRepository repo;
    private final FanRepository users;
    private final SecureRandom rnd = new SecureRandom();

    public record AutoLogin(Fan user, RememberMeToken record) {
    }

    public RememberMeService(RememberMeRepository repo, FanRepository users) {
        this.repo = repo;
        this.users = users;
    }

    // cookie-value format: selector:secret (base64url)
    public Optional<AutoLogin> tryAutoLogin(String cookieValue) {
        String[] parts = cookieValue == null ? new String[0] : cookieValue.split(":");
        if (parts.length != 2) {
            return Optional.empty();
        }
        String selector = parts[0];
        String secret = parts[1];

        Optional<RememberMeToken> recOpt = repo.findById(selector);
        if (recOpt.isEmpty()) {
            return Optional.empty();
        }
        RememberMeToken rec = recOpt.get();
        if (rec.getExpiresAt().isBefore(Instant.now())) {
            return Optional.empty();
        }
        if (!BCrypt.checkpw(secret, rec.getTokenHash())) {
            return Optional.empty();
        }
        Optional<Fan> optionalFan = users.findById(rec.getFanId());
        return optionalFan.map(fan -> new AutoLogin(fan, rec));
    }

    public String createCookieValue(Long userId) {
        String selector = base64Url(randomBytes(12));
        String secret = base64Url(randomBytes(32));
        String hash = BCrypt.hashpw(secret, BCrypt.gensalt());

        RememberMeToken rec = new RememberMeToken();
        rec.setSelector(selector);
        rec.setTokenHash(hash);
        rec.setFanId(userId);
        rec.setLastUsedAt(Instant.now());
        rec.setExpiresAt(Instant.now().plus(Duration.ofDays(30)));
        repo.save(rec);
        return selector + ":" + secret;
    }

    public String rotate(RememberMeToken rec) {
        String newSelector = base64Url(randomBytes(12));
        String newSecret = base64Url(randomBytes(32));
        rec.setSelector(newSelector);
        rec.setTokenHash(BCrypt.hashpw(newSecret, BCrypt.gensalt()));
        rec.setLastUsedAt(Instant.now());
        rec.setExpiresAt(Instant.now().plus(Duration.ofDays(30)));
        repo.save(rec);
        return newSelector + ":" + newSecret;
    }

    private byte[] randomBytes(int n) {
        byte[] b = new byte[n];
        rnd.nextBytes(b);
        return b;
    }

    private String base64Url(byte[] b) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(b);
    }
}
