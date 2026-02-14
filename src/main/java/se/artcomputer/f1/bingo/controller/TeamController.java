package se.artcomputer.f1.bingo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.artcomputer.f1.bingo.domain.TeamService;

@RestController
@RequestMapping("/public/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(final TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/{id}")
    public TeamDto getTeam(@PathVariable String id) {
        return teamService.findByCode(id);
    }
}
