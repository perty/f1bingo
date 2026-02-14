package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.controller.DriverDto;
import se.artcomputer.f1.bingo.controller.TeamDto;
import se.artcomputer.f1.bingo.entity.ContractEntity;
import se.artcomputer.f1.bingo.entity.DriverEntity;
import se.artcomputer.f1.bingo.entity.TeamEntity;
import se.artcomputer.f1.bingo.exception.NotFoundException;
import se.artcomputer.f1.bingo.repository.ContractRepository;
import se.artcomputer.f1.bingo.repository.DriverRepository;
import se.artcomputer.f1.bingo.repository.TeamRepository;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final ContractRepository contractRepository;
    private final DriverRepository driverRepository;

    public TeamService(TeamRepository teamRepository, ContractRepository contractRepository, DriverRepository driverRepository) {
        this.teamRepository = teamRepository;
        this.contractRepository = contractRepository;
        this.driverRepository = driverRepository;
    }

    public TeamDto findByCode(String codeString) {
        TeamCode code = TeamCode.valueOf(codeString);
        TeamEntity teamEntity = teamRepository.findByCode(code).orElseThrow(() -> new NotFoundException("Team " + code));
        return new TeamDto(
                teamEntity.getDisplayName(),
                carImage(teamEntity.getCode()),
                teamEntity.getEngine().name(),
                getDriverDtos(teamEntity.getCode(), teamEntity.getDisplayName()),
                teamEntity.getNationality().name(),
                flagImage(teamEntity.getNationality()),
                teamEntity.getOfficialName(),
                "Team Principal"
        );
    }

    private List<DriverDto> getDriverDtos(TeamCode teamCode, String displayName) {
        return contractRepository.findByTeam(teamCode)
                .stream()
                .map(contractEntity -> toDriverDto(contractEntity, displayName))
                .toList();
    }

    private DriverDto toDriverDto(ContractEntity contractEntity, String displayName) {
        DriverEntity driverEntity = driverRepository.findByCode(contractEntity.getDriver()).orElseThrow(() -> new NotFoundException("Driver " + contractEntity.getDriver()));
        return new DriverDto(
                driverEntity.getCode().name(),
                driverEntity.getNumber(),
                driverEntity.isYellowCam() ? "yellow-cam" : "",
                driverEntity.getFullName(),
                driverEntity.getNationality().getFileName(),
                flagImage(driverEntity.getNationality()),
                driverEntity.getLength(),
                driverEntity.getBorn(),
                contractEntity.getTeam(),
                displayName
        );
    }

    private static String flagImage(Country nationality) {
        return "/public/images/flag/" + nationality.getFileName() + ".png";
    }

    private static String carImage(TeamCode code) {
        return "/public/images/car/" + code.getFileName() + ".png";
    }
}
