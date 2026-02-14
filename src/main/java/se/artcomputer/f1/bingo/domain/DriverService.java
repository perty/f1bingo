package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.controller.DriverDto;
import se.artcomputer.f1.bingo.entity.DriverEntity;
import se.artcomputer.f1.bingo.exception.NotFoundException;
import se.artcomputer.f1.bingo.repository.DriverRepository;

import java.util.List;

@Service
public class DriverService {
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<DriverDto> findAll() {
        return driverRepository.findAll().stream().map(this::toDto).toList();
    }

    public DriverDto findByCode(String codeString) {
        DriverCode code =  DriverCode.valueOf(codeString);
        return toDto(driverRepository.findByCode(code).orElseThrow(() -> new NotFoundException("´Driver " + code)));
    }

    private DriverDto toDto(DriverEntity driverEntity) {
        return new DriverDto(
                driverEntity.getCode().name(),
                driverEntity.getNumber(),
                driverEntity.isYellowCam() ? "yellow-cam" : "",
                driverEntity.getFullName(),
                driverEntity.getNationality().getFileName(),
                flagImage(driverEntity.getNationality()),
                driverEntity.getLength(),
                driverEntity.getBorn()
        );
    }

    private static String flagImage(Country nationality) {
        return "/public/images/flag/" + nationality.getFileName() + ".png";
    }
}
