package se.artcomputer.f1.bingo.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public record CheckStatementsDto(List<VerifyStatementDto> statements,
                                 Optional<Date> created) {
}
