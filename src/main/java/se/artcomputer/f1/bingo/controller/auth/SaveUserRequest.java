package se.artcomputer.f1.bingo.controller.auth;

public record SaveUserRequest(String name, String roles, String password) {
}
