package se.artcomputer.f1.bingo.controller.auth;

import se.artcomputer.f1.bingo.entity.Fan;

import java.util.Optional;

public class UserContext {
    private static final ThreadLocal<Fan> CURRENT = new ThreadLocal<>();

    public static void set(Fan u) {
        CURRENT.set(u);
    }

    public static Optional<Fan> get() {
        return Optional.ofNullable(CURRENT.get());
    }

}
