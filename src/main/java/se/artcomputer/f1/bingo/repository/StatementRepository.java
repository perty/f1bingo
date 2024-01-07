package se.artcomputer.f1.bingo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.artcomputer.f1.bingo.entity.Statement;

import java.util.stream.Stream;

public interface StatementRepository extends JpaRepository<Statement, Long> {
    Stream<Statement> findByQualifying(boolean b);

    Stream<Statement> findByRace(boolean b);

    Stream<Statement> findBySprintRace(boolean b);

    Stream<Statement> findBySprintShootout(boolean b);
}
