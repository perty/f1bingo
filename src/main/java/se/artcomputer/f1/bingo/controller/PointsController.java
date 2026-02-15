package se.artcomputer.f1.bingo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.artcomputer.f1.bingo.domain.PointsService;

@RestController
@RequestMapping("/points")
public class PointsController {

    private final PointsService pointsService;

    public PointsController(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @GetMapping("/results/{year}")
    public ResultsDto getResults(@PathVariable("year") final int year) {
        return pointsService.getResults(year);
    }
}
