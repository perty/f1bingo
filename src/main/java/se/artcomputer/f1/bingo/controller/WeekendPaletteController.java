package se.artcomputer.f1.bingo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.artcomputer.f1.bingo.domain.WeekendPaletteService;
import se.artcomputer.f1.bingo.entity.BingoCard;
import se.artcomputer.f1.bingo.entity.BingoCardStatement;
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

    private WeekendPaletteDto toDto(WeekendPalette weekendPalette) {
        return new WeekendPaletteDto(
                weekendPalette.getFan().getName(),
                weekendPalette.getRaceWeekend().getName(),
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
                bingoCardStatement.getRow(),
                bingoCardStatement.getColumn(),
                bingoCardStatement.getStatement().getText(),
                bingoCardStatement.getChecked()
        );
    }
}
