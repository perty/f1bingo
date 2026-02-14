package se.artcomputer.f1.bingo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.artcomputer.f1.bingo.entity.DriverEntity;

public interface DriverRepository extends JpaRepository<DriverEntity, Long> {
}
