package se.artcomputer.f1.bingo.controller;

import java.util.List;

public record WeekendPaletteDto(
        Long fanId,
        String fanName,
        Long weekendId,
        String raceWeekendName,
        List<BingoCardDto> bingoCards
) {

}
