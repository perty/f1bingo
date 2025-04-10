package se.artcomputer.f1.bingo.controller.publicapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.artcomputer.f1.bingo.domain.RaceService;
import se.artcomputer.f1.bingo.domain.RaceWeekendType;
import se.artcomputer.f1.bingo.entity.RaceWeekend;

import java.util.List;

@RestController
@RequestMapping("public/calendar")
public class CalendarController {
    private final RaceService raceService;

    public CalendarController(RaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping
    public List<CalendarDto> races() {
        List<RaceWeekend> races = raceService.getRaceWeekends();
        return races.stream().map(this::toDto).toList();
    }

    @GetMapping("/{year}")
    public List<CalendarDto> races(@PathVariable int year) {
        List<RaceWeekend> races = raceService.getRaceWeekends(year);
        return races.stream().map(this::toDto).toList();
    }

    private CalendarDto toDto(RaceWeekend raceWeekend) {
        return new CalendarDto(
                raceWeekend.nameWithDates(),
                translateType(raceWeekend.getType()),
                raceWeekend.getCountry());
    }

    // Translate a type to a string
    private String translateType(RaceWeekendType type) {
        return switch (type) {
            case CLASSIC -> "klassisk";
            case SPRINT -> "sprint";
            case TBD -> "TBD";
        };
    }
}
