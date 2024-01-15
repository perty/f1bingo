package se.artcomputer.f1.bingo.controller;

import se.artcomputer.f1.bingo.domain.CheckState;

public record BingoCardStatementDto(long id,
                                    int row,
                                    int column,
                                    String text,
                                    CheckState checked,
                                    boolean bingo) {}
