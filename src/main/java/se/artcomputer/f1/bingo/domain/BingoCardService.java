package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.artcomputer.f1.bingo.entity.*;
import se.artcomputer.f1.bingo.repository.BingoCardStatementRepository;
import se.artcomputer.f1.bingo.repository.StatementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BingoCardService {

    private final StatementRepository statementRepository;
    private final BingoCardStatementRepository bingoCardStatementRepository;

    public BingoCardService(StatementRepository statementRepository, BingoCardStatementRepository bingoCardStatementRepository) {
        this.statementRepository = statementRepository;
        this.bingoCardStatementRepository = bingoCardStatementRepository;
    }

    @Transactional
    public List<BingoCard> createBingoCards(WeekendPalette weekendPalette, RaceWeekend raceWeekend) {
        return switch (raceWeekend.getType()) {
            case CLASSIC -> createBingoCardsClassic(weekendPalette);
            case SPRINT -> createBingoCardsSprint(weekendPalette);
        };
    }

    public void click(long cellId) {
        BingoCardStatement bingoCardStatement = bingoCardStatementRepository.findById(cellId).orElseThrow();
        bingoCardStatement.setChecked(switch (bingoCardStatement.getChecked()) {
            case POSSIBLE -> CheckState.HAPPENED;
            case HAPPENED -> CheckState.IMPOSSIBLE;
            case IMPOSSIBLE -> CheckState.POSSIBLE;
        });
        bingoCardStatementRepository.save(bingoCardStatement);
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
        List<Statement> takenStatements = new ArrayList<>();
        for (int row = 0; row < BingoCard.ROWS; row++) {
            for (int column = 0; column < BingoCard.COLS; column++) {
                BingoCardStatement bingoCardStatement = findStatement(bingoCard.getSession(), bingoCard, row, column, takenStatements);
                bingoCard.getBingoCardStatements().add(bingoCardStatement);
                takenStatements.add(bingoCardStatement.getStatement());
            }
        }
    }

    private BingoCardStatement findStatement(Session session, BingoCard bingoCard, int row, int column, List<Statement> takenStatements) {
        BingoCardStatement bingoCardStatement = new BingoCardStatement();
        bingoCardStatement.setBingoCard(bingoCard);
        bingoCardStatement.setRow(row);
        bingoCardStatement.setColumn(column);
        bingoCardStatement.setStatement(findStatementForSession(session, takenStatements).orElseThrow());
        return bingoCardStatement;
    }

    private Optional<Statement> findStatementForSession(Session session, List<Statement> takenStatements) {
        return switch (session) {
            case QUALIFYING -> selectRandom(statementRepository.findByQualifying(true).toList(), takenStatements);
            case RACE -> selectRandom(statementRepository.findByRace(true).toList(), takenStatements);
            case SPRINT_RACE -> selectRandom(statementRepository.findBySprintRace(true).toList(), takenStatements);
            case SPRINT_SHOOTOUT ->
                    selectRandom(statementRepository.findBySprintShootout(true).toList(), takenStatements);
        };
    }

    private Optional<Statement> selectRandom(List<Statement> allStatements, List<Statement> takenStatements) {
        if (allStatements.isEmpty()) {
            return Optional.empty();
        }
        int index = (int) (Math.random() * allStatements.size());
        Statement selectedStatement = allStatements.get(index);
        if (takenStatements.contains(selectedStatement)) {
            return selectRandom(allStatements, takenStatements);
        }
        return Optional.of(selectedStatement);
    }

    public void checkBingo(List<BingoCard> bingoCards) {
        for (BingoCard bingoCard : bingoCards) {
            checkBingo(bingoCard);
        }
    }

    private void checkBingo(BingoCard bingoCard) {
        Set<BingoCardStatement> bingoCardStatements = bingoCard.getBingoCardStatements();
        for (int row = 0; row < BingoCard.ROWS; row++) {
            int finalRow = row;
            List<BingoCardStatement> list = bingoCardStatements.stream().filter(bingoCardStatement -> bingoCardStatement.getRow() == finalRow).toList();
            if (checkRow(list)) {
                setBingo(list);
            }
        }
        for (int column = 0; column < BingoCard.COLS; column++) {
            int finalColumn = column;
            List<BingoCardStatement> list = bingoCardStatements.stream().filter(bingoCardStatement -> bingoCardStatement.getColumn() == finalColumn).toList();
            if (checkRow(list)) {
                setBingo(list);
            }
        }
        List<BingoCardStatement> diagDownRight = bingoCardStatements.stream().filter(bingoCardStatement -> bingoCardStatement.getRow() == bingoCardStatement.getColumn()).toList();
        if (checkRow(diagDownRight)) {
            setBingo(diagDownRight);
        }
        List<BingoCardStatement> diagUpLeft = bingoCardStatements.stream().filter(bingoCardStatement -> bingoCardStatement.getRow() + bingoCardStatement.getColumn() == BingoCard.COLS - 1).toList();
        if (checkRow(diagUpLeft)) {
            setBingo(diagUpLeft);
        }
    }

    private boolean checkRow(List<BingoCardStatement> list) {
        return list.stream().allMatch(bingoCardStatement -> bingoCardStatement.getChecked() == CheckState.HAPPENED);
    }

    private void setBingo(List<BingoCardStatement> list) {
        for (BingoCardStatement bingoCardStatement : list) {
            bingoCardStatement.bingo = true;
        }
    }
}
