package se.artcomputer.f1.bingo.controller;

import se.artcomputer.f1.bingo.domain.StatementCategory;

public record StatementRequest(String text, StatementCategory category) {
}
