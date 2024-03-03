package se.artcomputer.f1.bingo.controller;

import java.util.Date;
import java.util.Optional;

public record CheckStatementsDto(java.util.Set<VerifyStatementDto> statements,
                                 Optional<Date> created) {
}
