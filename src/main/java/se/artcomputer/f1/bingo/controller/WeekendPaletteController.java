package se.artcomputer.f1.bingo.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import se.artcomputer.f1.bingo.controller.util.GetUserDetails;
import se.artcomputer.f1.bingo.domain.SessionScheduleService;
import se.artcomputer.f1.bingo.domain.VerifyService;
import se.artcomputer.f1.bingo.domain.WeekendPaletteService;
import se.artcomputer.f1.bingo.entity.*;

import java.time.Instant;
import java.util.Comparator;
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

    @GetMapping("/weekend/{weekendId}")
    public WeekendPaletteDto getWeekendPalette(@PathVariable Long weekendId) {
        Optional<WeekendPalette> weekendPalette = GetUserDetails.doIfLoggedIn((userDetails) -> weekendPaletteService.getWeekendPalette(weekendId, userDetails), Optional.empty());
        return toDto(weekendPalette.orElseThrow());
    }

    @PostMapping("/weekend/{weekendId}/click")
    public WeekendPaletteDto postClick(@PathVariable Long weekendId, @RequestBody ClickRequest clickRequest) {
        Optional<UserDetails> loggedInUserDetails = GetUserDetails.getLoggedInUserDetails();
        if (loggedInUserDetails.isPresent()) {
            weekendPaletteService.click(clickRequest.cellId());
            WeekendPalette weekendPalette = weekendPaletteService.getWeekendPalette(weekendId, loggedInUserDetails.get()).orElseThrow();
            return toDto(weekendPalette);
        }
        return getWeekendPalette(weekendId);
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
                toFlag(raceWeekend.getCountry()),
                raceWeekend.getTrack(),
                weekendPalette.getBingoCards().stream()
                        .sorted(Comparator.comparing(bc -> bc.getSession().getSortOrder()))
                        .map(bingoCard -> toDto(bingoCard, raceWeekend)).toList()
        );
    }

    private String toFlag(String country) {
        return switch (country) {
            case "United States" -> "usa";
            case "United Arab Emirates" -> "abu-dhabi";
            case "United Kingdom" -> "great-britain";
            default -> country.toLowerCase().replace(" ", "-");
        };
    }

    private BingoCardDto toDto(BingoCard bingoCard, RaceWeekend weekend) {
        Optional<VerifiedSession> verifiedSession = verifyService.getVerifiedSession(weekend.getId(), bingoCard.getSession().name());
        Optional<Instant> utcStartTime = sessionScheduleService.findSessionStart(weekend.getId(), bingoCard.getSession());
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
