package se.artcomputer.f1.bingo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.artcomputer.f1.bingo.entity.BingoCard;
import se.artcomputer.f1.bingo.entity.BingoCardStatement;

import java.util.List;

public interface BingoCardStatementRepository extends JpaRepository<BingoCardStatement, Long> {
    List<BingoCardStatement> findByBingoCard(BingoCard bingoCard);
}
