package se.artcomputer.f1.bingo.controller;

import java.util.List;

public record BingoCardDto(String session, List<BingoCardStatementDto> statements) {
}
