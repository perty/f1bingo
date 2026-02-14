package se.artcomputer.f1.bingo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.artcomputer.f1.bingo.domain.DriverCode;
import se.artcomputer.f1.bingo.domain.TeamCode;
import se.artcomputer.f1.bingo.entity.ContractEntity;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<ContractEntity, Long> {
    Optional<ContractEntity> findByDriver(DriverCode code);

    List<ContractEntity> findByTeam(TeamCode teamCode);
}
