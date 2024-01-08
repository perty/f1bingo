package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.artcomputer.f1.bingo.entity.*;
import se.artcomputer.f1.bingo.repository.StatementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BingoCardService {

    private final StatementRepository statementRepository;

    public BingoCardService(StatementRepository statementRepository) {
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

    private List<BingoCard> createBingoCardsSprint(WeekendPalette weekendPalette) {
        BingoCard qualificationCard = createBingoCard(weekendPalette, Session.QUALIFYING);
        BingoCard raceCard = createBingoCard(weekendPalette, Session.RACE);
        BingoCard shootOutCard = createBingoCard(weekendPalette, Session.SPRINT_SHOOTOUT);
        BingoCard sprintRaceCard = createBingoCard(weekendPalette, Session.SPRINT_RACE);
        return List.of(qualificationCard, shootOutCard, sprintRaceCard, raceCard);
    }

    private BingoCard createBingoCard(WeekendPalette weekendPalette, Session session) {
        BingoCard bingoCard = new BingoCard();
        bingoCard.setWeekendPalette(weekendPalette);
        bingoCard.setSession(session);
        addStatementsToCard(bingoCard);
        return bingoCard;
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
            case QUALIFYING -> selectRandom(statementRepository.findByQualifying(true).toList());
            case RACE -> selectRandom(statementRepository.findByRace(true).toList());
            case SPRINT_RACE -> selectRandom(statementRepository.findBySprintRace(true).toList());
            case SPRINT_SHOOTOUT -> selectRandom(statementRepository.findBySprintShootout(true).toList());
        };
    }

    private Optional<Statement> selectRandom(List<Statement> list) {
        if(list.isEmpty()) {
            return Optional.empty();
        }
        int index = (int) (Math.random() * list.size());
        return Optional.of(list.get(index));
    }
}
