package se.artcomputer.f1.bingo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.artcomputer.f1.bingo.domain.PointsService;
import se.artcomputer.f1.bingo.domain.StandingsDto;

import java.util.List;

@RestController
@RequestMapping("/points")
public class PointsController {

    private final PointsService pointsService;

    public PointsController(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @GetMapping("/standings")
    public StandingsDto getStandings() {
        return pointsService.getStandings();
    }

    @GetMapping("/results")
    public ResultsDto getResults() {
        return new ResultsDto(
                List.of("Imola", "Portimao", "Barcelona"),
                List.of(
                        new ResultFanDto("Pelle", List.of(1, 2, 1)),
                        new ResultFanDto("Morgan", List.of(0, 1, 2))
                ));
    }
}
