package se.artcomputer.f1.bingo.controller.auth;


import se.artcomputer.f1.bingo.entity.Fan;

import java.util.List;

public record AllUsersResponse(List<Fan> users) {
    public static AllUsersResponse from(List<Fan> users) {
        return new AllUsersResponse(users);
    }
}
