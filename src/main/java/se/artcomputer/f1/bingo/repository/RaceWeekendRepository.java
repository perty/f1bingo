package se.artcomputer.f1.bingo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.artcomputer.f1.bingo.entity.RaceWeekend;

import java.util.List;

public interface RaceWeekendRepository extends JpaRepository<RaceWeekend, Long> {
}
