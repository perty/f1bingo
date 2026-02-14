package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.controller.DriverDto;
import se.artcomputer.f1.bingo.controller.TeamDto;
import se.artcomputer.f1.bingo.entity.TeamEntity;
import se.artcomputer.f1.bingo.exception.NotFoundException;
import se.artcomputer.f1.bingo.repository.TeamRepository;

import java.util.Collections;
import java.util.Date;
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

    public TeamDto findByCode(String codeString) {
        TeamCode code = TeamCode.valueOf(codeString);
        TeamEntity teamEntity = teamRepository.findByCode(code).orElseThrow(() -> new NotFoundException("Team " + code));
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

    private static List<DriverDto> getDriverDtos() {
        Date born =  new Date();
        return List.of(
                new DriverDto(
                        "str",
                        1,
                        "",
                        "Black driver",
                        Country.australia.name(),
                        flagImage(Country.australia),
                        180,
                        born
                ),
                new DriverDto(
                        "alo",
                        55,
                        "yellow-cam",
                        "Yellow driver",
                        Country.australia.name(),
                        flagImage(Country.italy),
                        180,
                        born
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
