package se.artcomputer.f1.bingo.controller.auth;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public class GetUserDetails {

    public static Optional<FanDetails> getLoggedInUserDetails() {
        return UserContext.get().map(fan -> new FanDetails(fan.getName(), Arrays.stream(fan.getRoles().split(",")).toList()));
    }

    public static <T> T doIfLoggedIn(Function<FanDetails, T> serviceFunction, final T defaultValue) {
        return getLoggedInUserDetails()
                .map(serviceFunction)
                .orElse(defaultValue);
    }

}
