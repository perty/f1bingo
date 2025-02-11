package se.artcomputer.f1.bingo.controller.auth;

public record SaveUserRequest(String email, String roles, String password) {
}
