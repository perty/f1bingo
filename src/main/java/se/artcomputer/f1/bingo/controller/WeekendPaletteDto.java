package se.artcomputer.f1.bingo.controller;

import java.util.Date;
import java.util.List;

public record WeekendPaletteDto(
        Long fanId,
        String fanName,
        Long weekendId,
        String raceWeekendName,
        Date startDate,
        Date endDate,
        String country,
        String track,
        List<BingoCardDto> bingoCards
) {

}
