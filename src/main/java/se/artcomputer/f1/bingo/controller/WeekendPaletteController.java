package se.artcomputer.f1.bingo.controller;

import org.springframework.web.bind.annotation.*;
import se.artcomputer.f1.bingo.domain.WeekendPaletteService;
import se.artcomputer.f1.bingo.entity.BingoCard;
import se.artcomputer.f1.bingo.entity.BingoCardStatement;
import se.artcomputer.f1.bingo.entity.RaceWeekend;
import se.artcomputer.f1.bingo.entity.WeekendPalette;

@RequestMapping("palette")
@RestController
public class WeekendPaletteController {

    private final WeekendPaletteService weekendPaletteService;

    public WeekendPaletteController(WeekendPaletteService weekendPaletteService) {
        this.weekendPaletteService = weekendPaletteService;
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
                raceWeekend.getName(),
                raceWeekend.getStartDate(),
                raceWeekend.getEndDate(),
                raceWeekend.getCountry(),
                raceWeekend.getTrack(),
                weekendPalette.getBingoCards().stream().map(this::toDto).toList()
        );
    }

    private BingoCardDto toDto(BingoCard bingoCard) {
        return new BingoCardDto(
                bingoCard.getSession().name(),
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
