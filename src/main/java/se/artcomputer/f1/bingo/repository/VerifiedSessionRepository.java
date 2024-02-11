package se.artcomputer.f1.bingo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import se.artcomputer.f1.bingo.domain.Session;
import se.artcomputer.f1.bingo.entity.RaceWeekend;
import se.artcomputer.f1.bingo.entity.VerifiedSession;

import java.util.Optional;

public interface VerifiedSessionRepository extends JpaRepository<VerifiedSession, Long> {
    Optional<VerifiedSession> findByRaceWeekendAndSession(RaceWeekend raceWeekend, Session session);

    @Transactional
    void deleteByRaceWeekendIdAndSession(Long weekendId, Session session);
}
