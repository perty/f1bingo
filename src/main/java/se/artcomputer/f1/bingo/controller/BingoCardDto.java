package se.artcomputer.f1.bingo.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public record BingoCardDto(String session,
                           Optional<Date> verified,
                           Optional<java.time.Instant> utcStart,
                           List<BingoCardStatementDto> statements
) {
}
