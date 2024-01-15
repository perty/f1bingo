package se.artcomputer.f1.bingo.controller;

// A rest controller for race weekends.


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.artcomputer.f1.bingo.domain.RaceService;
import se.artcomputer.f1.bingo.entity.RaceWeekend;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("weekend")
public class RaceWeekendController {

    private final RaceService raceService;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");

    public RaceWeekendController(RaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping
    public List<RaceWeekendDto> races() {
        List<RaceWeekend> races = raceService.getRaceWeekends();
        return races.stream().map(this::toDto).toList();
    }

    private RaceWeekendDto toDto(RaceWeekend raceWeekend) {
        return new RaceWeekendDto(raceWeekend.getId(), "%s %s - %s".formatted(
                raceWeekend.getName(),
                formatter.format(raceWeekend.getStartDate()),
                formatter.format(raceWeekend.getEndDate())));
    }
}
