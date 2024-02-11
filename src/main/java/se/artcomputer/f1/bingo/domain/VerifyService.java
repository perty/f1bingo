package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.entity.BingoCardStatement;
import se.artcomputer.f1.bingo.entity.RaceWeekend;
import se.artcomputer.f1.bingo.entity.VerifiedSession;
import se.artcomputer.f1.bingo.repository.RaceWeekendRepository;
import se.artcomputer.f1.bingo.repository.StatementRepository;
import se.artcomputer.f1.bingo.repository.VerifiedSessionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VerifyService {
    private final BingoCardService bingoCardService;
    private final VerifiedSessionRepository verifiedSessionRepository;
    private final StatementRepository statementRepository;
    private final RaceWeekendRepository raceWeekendRepository;

    public VerifyService(BingoCardService bingoCardService, VerifiedSessionRepository verifiedSessionRepository,
                         StatementRepository statementRepository,
                         RaceWeekendRepository raceWeekendRepository) {
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
                    statementRepository.findById(verifiedStatement.id()).ifPresent(verifiedSession::add);
                }
            }
            verifiedSessionRepository.save(verifiedSession);
        }
    }

    public List<VerifiedSession> getVerifiedSessions() {
        return verifiedSessionRepository.findAll();
    }
}
