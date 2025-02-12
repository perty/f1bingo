package se.artcomputer.f1.bingo.controller.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.function.Function;

public class GetUserDetails {

    public static Optional<UserDetails> getLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return Optional.of((UserDetails) authentication.getPrincipal());
        }
        return Optional.empty();
    }

    public static <T> T doIfLoggedIn(Function<UserDetails, T> serviceFunction, final T defaultValue) {
        return getLoggedInUserDetails()
                .map(serviceFunction)
                .orElse(defaultValue);
    }

}
