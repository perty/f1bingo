package se.artcomputer.f1.bingo.controller;

public record StatementUpdateRequest(String text,
                                     String category,
                                     boolean enabled,
                                     boolean race,
                                     boolean qualifying,
                                     boolean sprintShootout,
                                     boolean sprintRace) {
}
