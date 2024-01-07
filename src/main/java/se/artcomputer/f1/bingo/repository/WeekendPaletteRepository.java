package se.artcomputer.f1.bingo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.artcomputer.f1.bingo.entity.Fan;
import se.artcomputer.f1.bingo.entity.RaceWeekend;
import se.artcomputer.f1.bingo.entity.WeekendPalette;

import java.util.Optional;

public interface WeekendPaletteRepository extends JpaRepository<WeekendPalette, Long>  {
    Optional<WeekendPalette> findByRaceWeekendAndFan(RaceWeekend weekendId, Fan fan);
}
