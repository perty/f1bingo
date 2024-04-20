package se.artcomputer.f1.bingo.controller;

import org.springframework.web.bind.annotation.*;
import se.artcomputer.f1.bingo.domain.SessionScheduleService;
import se.artcomputer.f1.bingo.domain.VerifyService;
import se.artcomputer.f1.bingo.domain.WeekendPaletteService;
import se.artcomputer.f1.bingo.entity.*;

import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

@RequestMapping("palette")
@RestController
public class WeekendPaletteController {

    private final WeekendPaletteService weekendPaletteService;
    private final VerifyService verifyService;
    private final SessionScheduleService sessionScheduleService;

    public WeekendPaletteController(WeekendPaletteService weekendPaletteService,
                                    VerifyService verifyService,
                                    SessionScheduleService sessionScheduleService) {
        this.weekendPaletteService = weekendPaletteService;
        this.verifyService = verifyService;
        this.sessionScheduleService = sessionScheduleService;
    }

    @GetMapping("/weekend/{weekendId}/fan/{fanId}")
    public WeekendPaletteDto getWeekendPalette(@PathVariable Long weekendId, @PathVariable Long fanId) {
        return toDto(weekendPaletteService.getWeekendPalette(weekendId, fanId));
    }

    @PostMapping("/weekend/{weekendId}/fan/{fanId}/click")
    public WeekendPaletteDto postClick(@PathVariable Long weekendId, @PathVariable Long fanId,
                                       @RequestBody ClickRequest clickRequest) {
        weekendPaletteService.click(clickRequest.cellId());
        return toDto(weekendPaletteService.getWeekendPalette(weekendId, fanId));
    }

    private WeekendPaletteDto toDto(WeekendPalette weekendPalette) {
        RaceWeekend raceWeekend = weekendPalette.getRaceWeekend();
        return new WeekendPaletteDto(
                weekendPalette.getFan().getId(),
                weekendPalette.getFan().getName(),
                raceWeekend.getId(),
                raceWeekend.nameWithDates(),
                raceWeekend.getStartDate(),
                raceWeekend.getEndDate(),
                raceWeekend.getCountry(),
                raceWeekend.getTrack(),
                weekendPalette.getBingoCards().stream()
                        .sorted(Comparator.comparing(bc -> bc.getSession().getSortOrder()))
                        .map(bingoCard -> toDto(bingoCard, raceWeekend)).toList()
        );
    }

    private BingoCardDto toDto(BingoCard bingoCard, RaceWeekend weekend) {
        Optional<VerifiedSession> verifiedSession = verifyService.getVerifiedSession(weekend.getId(), bingoCard.getSession().name());
        Optional<Date> utcStartTime = sessionScheduleService.findSessionStart(weekend.getId(), bingoCard.getSession());
        return new BingoCardDto(
                bingoCard.getSession().name(),
                verifiedSession.map(VerifiedSession::getCreated),
                utcStartTime,
                bingoCard.getBingoCardStatements().stream().map(this::toDto).toList()
        );
    }

    private BingoCardStatementDto toDto(BingoCardStatement bingoCardStatement) {
        return new BingoCardStatementDto(
                bingoCardStatement.getId(),
                bingoCardStatement.getRow(),
                bingoCardStatement.getColumn(),
                bingoCardStatement.getStatement().getText(),
                bingoCardStatement.getChecked(),
                bingoCardStatement.bingo
        );
    }
}
