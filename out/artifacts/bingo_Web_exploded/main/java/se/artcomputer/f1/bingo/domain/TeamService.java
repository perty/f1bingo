package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.controller.TeamDto;
import se.artcomputer.f1.bingo.entity.TeamEntity;
import se.artcomputer.f1.bingo.repository.TeamRepository;

import java.util.Collections;
import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<TeamDto> findAll() {
        return Collections.emptyList();
    }

    public TeamDto findByCode(String code) {
        //TeamEntity teamEntity = teamRepository.findByCode(code).orElseThrow(() -> new NotFoundException("Team " + code));
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setCode(TeamCode.ferrari);
        teamEntity.setDisplayName("Team display name");
        teamEntity.setOfficialName("Team official name");
        teamEntity.setNationality(Country.argentina);
        teamEntity.setEngine(Engine.Ferrai);
        return new TeamDto(
                teamEntity.getDisplayName(),
                carImage(teamEntity.getCode()),
                teamEntity.getEngine().name(),
                getDriverDtos(),
                teamEntity.getNationality().name(),
                flagImage(teamEntity.getNationality()),
                teamEntity.getOfficialName(),
                "Team Principal"
                );
    }

    private static List<TeamDto.DriverDto> getDriverDtos() {
        return List.of(
                new TeamDto.DriverDto(
                        1,
                        "",
                        "Black driver",
                        Country.australia.name(),
                        flagImage(Country.australia)
                ),
                new TeamDto.DriverDto(
                        55,
                        "yellow-cam",
                        "Yellow driver",
                        Country.australia.name(),
                        flagImage(Country.italy)
                )
        );
    }

    private static String flagImage(Country nationality) {
        return "/public/images/flag/" + nationality.getFileName() + ".png";
    }

    private static String carImage(TeamCode code) {
        return "/public/images/car/"  + code.getFileName() + ".png";
    }
}
