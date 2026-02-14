package se.artcomputer.f1.bingo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.artcomputer.f1.bingo.domain.DriverCode;
import se.artcomputer.f1.bingo.entity.DriverEntity;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<DriverEntity, Long> {
    Optional<DriverEntity> findByCode(DriverCode code);
}
