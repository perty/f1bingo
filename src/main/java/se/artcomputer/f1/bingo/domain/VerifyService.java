package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.entity.*;
import se.artcomputer.f1.bingo.repository.RaceWeekendRepository;
import se.artcomputer.f1.bingo.repository.StatementRepository;
import se.artcomputer.f1.bingo.repository.VerifiedSessionRepository;
import se.artcomputer.f1.bingo.repository.VerifiedStatementRepository;

import java.util.*;

@Service
public class VerifyService {
    private final VerifiedStatementRepository verifiedStatementRepository;
    private final BingoCardService bingoCardService;
    private final VerifiedSessionRepository verifiedSessionRepository;
    private final StatementRepository statementRepository;
    private final RaceWeekendRepository raceWeekendRepository;

    public VerifyService(VerifiedStatementRepository verifiedStatementRepository,
                         BingoCardService bingoCardService,
                         VerifiedSessionRepository verifiedSessionRepository,
                         StatementRepository statementRepository,
                         RaceWeekendRepository raceWeekendRepository) {
        this.verifiedStatementRepository = verifiedStatementRepository;
        this.bingoCardService = bingoCardService;
        this.verifiedSessionRepository = verifiedSessionRepository;
        this.statementRepository = statementRepository;
        this.raceWeekendRepository = raceWeekendRepository;
    }

    public List<BingoCardStatement> getCheckStatements(Long weekendId, String sessionName) {
        Session session = Session.valueOf(sessionName.toUpperCase());
        return bingoCardService.getStatementsForWeekend(weekendId, session);
    }

    public Optional<VerifiedSession> getVerifiedSession(Long weekendId, String sessionName) {
        Session session = Session.valueOf(sessionName.toUpperCase());
        RaceWeekend raceWeekend = raceWeekendRepository.findById(weekendId).orElseThrow();
        return verifiedSessionRepository.findByRaceWeekendAndSession(raceWeekend, session);
    }

    public void toggleCloseSession(List<VerifiedStatement> verifiedStatements, Long weekendId, Session session) {
        if (getVerifiedSession(weekendId, session.name()).isPresent()) {
            verifiedSessionRepository.deleteByRaceWeekendIdAndSession(weekendId, session);
        } else {
            VerifiedSession verifiedSession = new VerifiedSession();
            raceWeekendRepository.findById(weekendId).ifPresent(verifiedSession::setWeekend);
            verifiedSession.setSession(session);
            for (VerifiedStatement verifiedStatement : verifiedStatements) {
                if (verifiedStatement.verified()) {
                    statementRepository.findById(verifiedStatement.id())
                            .ifPresent(statement -> addAndSave(statement, verifiedSession));
                }
            }
            verifiedSessionRepository.save(verifiedSession);
        }
    }

    private void addAndSave(Statement statement, VerifiedSession verifiedSession) {
        VerifiedStatementEntity verifiedStatementEntity = verifiedSession.add(statement);
        verifiedStatementRepository.save(verifiedStatementEntity);
    }

    public List<VerifiedSession> getVerifiedSessions(final int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, 1, 0, 0, 0);
        Date startOfYear = calendar.getTime();
        return verifiedSessionRepository
                .findAll()
                .stream()
                .filter(verifiedSession -> isAfter(verifiedSession, startOfYear))
                .sorted(Comparator.comparing(VerifiedSession::getStartDate)).toList();
    }

    private static boolean isAfter(VerifiedSession verifiedSession, Date startOfYear) {
        Date startDate = verifiedSession.getRaceWeekend().getStartDate();
        return startDate.after(startOfYear);
    }
}
