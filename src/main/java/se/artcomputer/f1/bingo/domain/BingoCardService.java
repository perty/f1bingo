package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.artcomputer.f1.bingo.entity.*;
import se.artcomputer.f1.bingo.repository.BingoCardRepository;
import se.artcomputer.f1.bingo.repository.StatementRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BingoCardService {

    private final BingoCardRepository bingoCardRepository;
    private final StatementRepository statementRepository;

    public BingoCardService(BingoCardRepository bingoCardRepository, StatementRepository statementRepository) {
        this.bingoCardRepository = bingoCardRepository;
        this.statementRepository = statementRepository;
    }

    @Transactional
    public List<BingoCard> createBingoCards(WeekendPalette weekendPalette, RaceWeekend raceWeekend) {
        return switch (raceWeekend.getType()) {
            case CLASSIC -> createBingoCardsClassic(weekendPalette);
            case SPRINT -> createBingoCardsSprint(weekendPalette);
        };
    }

    private List<BingoCard> createBingoCardsClassic(WeekendPalette weekendPalette) {
        BingoCard qualificationCard = createBingoCard(weekendPalette, Session.QUALIFYING);
        BingoCard raceCard = createBingoCard(weekendPalette, Session.RACE);
        return List.of(qualificationCard, raceCard);
    }

    private BingoCard createBingoCard(WeekendPalette weekendPalette, Session session) {
        BingoCard qualificationCard = new BingoCard();
        qualificationCard.setWeekendPalette(weekendPalette);
        qualificationCard.setSession(session);
        addStatementsToCard(qualificationCard);
        return qualificationCard;
    }

    private void addStatementsToCard(BingoCard bingoCard) {
        for(int row = 0; row < BingoCard.ROWS; row++) {
            for(int column = 0; column < BingoCard.COLS; column++) {
                bingoCard.getStatements().add(findStatement(bingoCard.getSession(), bingoCard, row, column));
            }
        }
    }

    private BingoCardStatement findStatement(Session session, BingoCard bingoCard, int row, int column) {
        BingoCardStatement bingoCardStatement = new BingoCardStatement();
        bingoCardStatement.setBingoCard(bingoCard);
        bingoCardStatement.setRow(row);
        bingoCardStatement.setColumn(column);
        bingoCardStatement.setStatement(findStatementForSession(session).orElseThrow());
        return bingoCardStatement;
    }

    private Optional<Statement> findStatementForSession(Session session) {
        return switch (session) {
            case QUALIFYING -> statementRepository.findByQualifying(true).findFirst();
            case RACE -> statementRepository.findByRace(true).findFirst();
            case SPRINT_RACE -> statementRepository.findBySprintRace(true).findFirst();
            case SPRINT_SHOOTOUT -> statementRepository.findBySprintShootout(true).findFirst();
        };
    }

    private List<BingoCard> createBingoCardsSprint(WeekendPalette weekendPalette) {
        return Collections.emptyList();
    }
}
