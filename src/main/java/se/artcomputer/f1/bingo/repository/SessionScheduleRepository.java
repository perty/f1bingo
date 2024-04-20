package se.artcomputer.f1.bingo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.artcomputer.f1.bingo.entity.SessionSchedule;

import java.util.List;

public interface SessionScheduleRepository extends JpaRepository<SessionSchedule, Long> {
    List<SessionSchedule> findByLocationOrderByStartTime(String country);
}
