package se.artcomputer.f1.bingo.controller.auth;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoder {
    public String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean matches(String givenPassword, String hashedPassword) {
        return BCrypt.checkpw(givenPassword, hashedPassword);
    }
}
