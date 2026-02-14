package se.artcomputer.f1.bingo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.artcomputer.f1.bingo.domain.TeamService;

import java.util.List;

@RestController
@RequestMapping("/public/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(final TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<TeamDto> listTeams() {
        return teamService.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public TeamDto getTeam(@PathVariable String id) {
        return teamService.findByCode(id);
    }

    private TeamDto toDto(TeamDto teamDto) {
        return teamDto;
    }
}
