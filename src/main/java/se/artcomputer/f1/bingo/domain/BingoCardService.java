package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.artcomputer.f1.bingo.entity.*;
import se.artcomputer.f1.bingo.repository.*;

import java.util.*;

@Service
public class BingoCardService {

    private final StatementRepository statementRepository;
    private final BingoCardStatementRepository bingoCardStatementRepository;
    private final RaceWeekendRepository raceWeekendRepository;
    private final WeekendPaletteRepository weekendPaletteRepository;
    private final VerifiedSessionRepository verifiedSessionRepository;

    public BingoCardService(StatementRepository statementRepository,
                            BingoCardStatementRepository bingoCardStatementRepository,
                            RaceWeekendRepository raceWeekendRepository,
                            WeekendPaletteRepository weekendPaletteRepository,
                            VerifiedSessionRepository verifiedSessionRepository) {
        this.statementRepository = statementRepository;
        this.bingoCardStatementRepository = bingoCardStatementRepository;
        this.raceWeekendRepository = raceWeekendRepository;
        this.weekendPaletteRepository = weekendPaletteRepository;
        this.verifiedSessionRepository = verifiedSessionRepository;
    }

    @Transactional
    public List<BingoCard> createBingoCards(WeekendPalette weekendPalette, RaceWeekend raceWeekend) {
        return switch (raceWeekend.getType()) {
            case CLASSIC -> createBingoCardsClassic(weekendPalette);
            case SPRINT -> createBingoCardsSprint(weekendPalette);
            case TBD -> List.of();
        };
    }

    public long countBingoCardsWithClicks(RaceWeekend raceWeekend, Session session) {
        return weekendPaletteRepository.findByRaceWeekend(raceWeekend).stream()
                .flatMap(wc -> wc.getBingoCards().stream())
                .filter(bingoCard -> bingoCard.getSession().equals(session))
                .filter(bingoCard -> bingoCardStatementRepository.findByBingoCard(bingoCard).stream()
                        .anyMatch(bcs -> bcs.getChecked() != CheckState.POSSIBLE))
                .count();
    }

    @Transactional
    public void resetBingoCards(RaceWeekend raceWeekend, Session session) {
        List<BingoCard> cards = weekendPaletteRepository.findByRaceWeekend(raceWeekend).stream()
                .flatMap(wc -> wc.getBingoCards().stream())
                .filter(bingoCard -> bingoCard.getSession().equals(session))
                .toList();
        for (BingoCard bingoCard : cards) {
            List<BingoCardStatement> bingoCardStatements = bingoCardStatementRepository.findByBingoCard(bingoCard);
            for (BingoCardStatement bcs : bingoCardStatements) {
                bcs.setChecked(CheckState.POSSIBLE);
            }
            bingoCardStatementRepository.saveAll(bingoCardStatements);
        }
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
        RaceWeekend raceWeekend = weekendPalette.getRaceWeekend();
        List<Statement> verifiedStatements = verifiedSessionRepository.findByRaceWeekendAndSession(raceWeekend, session)
                .map(VerifiedSession::getStatements)
                .orElse(Collections.emptyList())
                .stream()
                .map(VerifiedStatementEntity::getStatement)
                .toList();
        BingoCard bingoCard = new BingoCard();
        bingoCard.setWeekendPalette(weekendPalette);
        bingoCard.setSession(session);
        if (verifiedStatements.isEmpty()) {
            addStatementsToCard(bingoCard);
        }
        return bingoCard;
    }

    private void addStatementsToCard(BingoCard bingoCard) {
        List<Statement> takenStatements = new ArrayList<>();
        Set<StatementCategory> takenCategories = new HashSet<>();
        for (int row = 0; row < BingoCard.ROWS; row++) {
            for (int column = 0; column < BingoCard.COLS; column++) {
                BingoCardStatement bingoCardStatement = findStatement(bingoCard.getSession(),
                        bingoCard,
                        row,
                        column,
                        takenStatements,
                        takenCategories);
                bingoCard.getBingoCardStatements().add(bingoCardStatement);
                takenStatements.add(bingoCardStatement.getStatement());
                if (!bingoCardStatement.getStatement().getCategory().equals(StatementCategory.NONE)) {
                    takenCategories.add(bingoCardStatement.getStatement().getCategory());
                }
            }
        }
    }

    private BingoCardStatement findStatement(Session session,
                                             BingoCard bingoCard,
                                             int row,
                                             int column,
                                             List<Statement> takenStatements,
                                             Set<StatementCategory> takenCategories) {
        BingoCardStatement bingoCardStatement = new BingoCardStatement();
        bingoCardStatement.setBingoCard(bingoCard);
        bingoCardStatement.setRow(row);
        bingoCardStatement.setColumn(column);
        bingoCardStatement.setStatement(findStatementForSession(session, takenStatements, takenCategories).orElseThrow());
        return bingoCardStatement;
    }

    private Optional<Statement> findStatementForSession(Session session, List<Statement> takenStatements, Set<StatementCategory> takenCategories) {
        return switch (session) {
            case QUALIFYING ->
                    selectRandom(statementRepository.findByQualifying(true).toList(), takenStatements, takenCategories);
            case RACE -> selectRandom(statementRepository.findByRace(true).toList(), takenStatements, takenCategories);
            case SPRINT_RACE ->
                    selectRandom(statementRepository.findBySprintRace(true).toList(), takenStatements, takenCategories);
            case SPRINT_SHOOTOUT ->
                    selectRandom(statementRepository.findBySprintShootout(true).toList(), takenStatements, takenCategories);
        };
    }

    private Optional<Statement> selectRandom(List<Statement> allStatements, List<Statement> takenStatements, Set<StatementCategory> takenCategories) {
        if (allStatements.isEmpty()) {
            return Optional.empty();
        }
        int index = (int) (Math.random() * allStatements.size());
        Statement selectedStatement = allStatements.get(index);
        if (!selectedStatement.isEnabled() ||
                takenStatements.contains(selectedStatement) ||
                takenCategories.contains(selectedStatement.getCategory())) {
            return selectRandom(allStatements, takenStatements, takenCategories);
        }
        return Optional.of(selectedStatement);
    }

    public void checkBingo(List<BingoCard> bingoCards) {
        for (BingoCard bingoCard : bingoCards) {
            checkBingo(bingoCard);
        }
    }

    public List<BingoCardStatement> getStatementsForWeekend(Long weekendId, Session session) {
        RaceWeekend raceWeekend = raceWeekendRepository.findById(weekendId).orElseThrow();
        return weekendPaletteRepository.findByRaceWeekend(raceWeekend).stream()
                .flatMap(wc -> wc.getBingoCards().stream()
                        .filter(bingoCard -> bingoCard.getSession() == session))
                .flatMap(bingoCard -> bingoCard.getBingoCardStatements().stream()
                        .filter(bcs -> bcs.getChecked() == CheckState.HAPPENED)).toList();
    }

    public long getPoints(BingoCard bingoCard) {
        return checkBingo(bingoCard);
    }

    private int checkBingo(BingoCard bingoCard) {
        int bingos = 0;
        List<Statement> verifiedSessionStatements =
                verifiedSessionRepository.findByRaceWeekendAndSession(bingoCard.getWeekendPalette().getRaceWeekend(), bingoCard.getSession())
                        .map(vs -> vs.getStatements().stream().map(VerifiedStatementEntity::getStatement).toList())
                        .orElse(Collections.emptyList());
        Set<BingoCardStatement> bingoCardStatements = bingoCard.getBingoCardStatements();
        for (int row = 0; row < BingoCard.ROWS; row++) {
            int finalRow = row;
            List<BingoCardStatement> list = bingoCardStatements.stream().filter(bingoCardStatement -> bingoCardStatement.getRow() == finalRow).toList();
            if (checkRow(list, verifiedSessionStatements)) {
                setBingo(list);
                bingos++;
            }
        }
        for (int column = 0; column < BingoCard.COLS; column++) {
            int finalColumn = column;
            List<BingoCardStatement> list = bingoCardStatements.stream().filter(bingoCardStatement -> bingoCardStatement.getColumn() == finalColumn).toList();
            if (checkRow(list, verifiedSessionStatements)) {
                setBingo(list);
                bingos++;
            }
        }
        List<BingoCardStatement> diagDownRight = bingoCardStatements.stream().filter(bingoCardStatement -> bingoCardStatement.getRow() == bingoCardStatement.getColumn()).toList();
        if (checkRow(diagDownRight, verifiedSessionStatements)) {
            setBingo(diagDownRight);
            bingos++;
        }
        List<BingoCardStatement> diagUpLeft = bingoCardStatements.stream().filter(bingoCardStatement -> bingoCardStatement.getRow() + bingoCardStatement.getColumn() == BingoCard.COLS - 1).toList();
        if (checkRow(diagUpLeft, verifiedSessionStatements)) {
            setBingo(diagUpLeft);
            bingos++;
        }
        return bingos;
    }

    private boolean checkRow(List<BingoCardStatement> list, List<Statement> verifiedSessionStatements) {
        if (list.isEmpty()) {
            return false;
        }
        if (verifiedSessionStatements.isEmpty()) {
            return list.stream().allMatch(bingoCardStatement -> bingoCardStatement.getChecked() == CheckState.HAPPENED);
        }
        return list.stream().allMatch(bingoCardStatement -> bingoCardStatement.getChecked() == CheckState.HAPPENED && verifiedSessionStatements.contains(bingoCardStatement.getStatement()));
    }

    private void setBingo(List<BingoCardStatement> list) {
        for (BingoCardStatement bingoCardStatement : list) {
            bingoCardStatement.bingo = true;
        }
    }
}
