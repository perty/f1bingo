package se.artcomputer.f1.bingo.controller.auth;

public record ChangePasswordRequest(String currentPassword, String newPassword) {
}
