package se.artcomputer.f1.bingo.controller.auth;


import se.artcomputer.f1.bingo.entity.OurUser;

import java.util.List;

public record AllUsersResponse(List<OurUser> users) {
    public static AllUsersResponse from(List<OurUser> users) {
        return new AllUsersResponse(users);
    }
}
