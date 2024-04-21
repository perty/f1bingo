package se.artcomputer.f1.bingo.controller;

public record StatementDto(Long id,
                           String text,
                           String category,
                           boolean enabled,
                           boolean race,
                           boolean qualifying,
                           boolean sprintShootout,
                           boolean sprintRace) {
}
