package se.artcomputer.f1.bingo.controller;

import se.artcomputer.f1.bingo.entity.BingoCard;

import java.util.List;

public record WeekendPaletteDto(
        String fanName,
        String raceWeekendName,
        List<BingoCardDto> bingoCards
) {

}
