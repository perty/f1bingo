package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.controller.DriverDto;
import se.artcomputer.f1.bingo.entity.ContractEntity;
import se.artcomputer.f1.bingo.entity.DriverEntity;
import se.artcomputer.f1.bingo.entity.TeamEntity;
import se.artcomputer.f1.bingo.exception.NotFoundException;
import se.artcomputer.f1.bingo.repository.ContractRepository;
import se.artcomputer.f1.bingo.repository.DriverRepository;
import se.artcomputer.f1.bingo.repository.TeamRepository;

import java.util.List;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final ContractRepository contractRepository;
    private final TeamRepository teamRepository;

    public DriverService(DriverRepository driverRepository, ContractRepository contractRepository, TeamRepository teamRepository) {
        this.driverRepository = driverRepository;
        this.contractRepository = contractRepository;
        this.teamRepository = teamRepository;
    }

    public List<DriverDto> findAll() {
        return driverRepository.findAll().stream().map(this::toDto).toList();
    }

    public DriverDto findByCode(String codeString) {
        DriverCode code =  DriverCode.valueOf(codeString);
        return toDto(driverRepository.findByCode(code).orElseThrow(() -> new NotFoundException("´Driver " + code)));
    }

    private DriverDto toDto(DriverEntity driverEntity) {
        ContractEntity contractEntity = contractRepository.findByDriver(driverEntity.getCode()).orElseThrow(() -> new NotFoundException("Contract for " + driverEntity.getCode()));
        TeamEntity teamEntity = teamRepository.findByCode(contractEntity.getTeam()).orElseThrow(() -> new NotFoundException("Team  " + contractEntity.getTeam()));

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
                teamEntity.getDisplayName()
        );
    }

    private static String flagImage(Country nationality) {
        return "/public/images/flag/" + nationality.getFileName() + ".png";
    }
}
