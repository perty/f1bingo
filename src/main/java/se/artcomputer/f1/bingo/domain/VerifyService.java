package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.entity.BingoCardStatement;
import se.artcomputer.f1.bingo.entity.VerifiedSession;
import se.artcomputer.f1.bingo.repository.RaceWeekendRepository;
import se.artcomputer.f1.bingo.repository.StatementRepository;
import se.artcomputer.f1.bingo.repository.VerifiedSessionRepository;

import java.util.Arrays;
import java.util.List;

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
        Session session = Arrays.stream(Session.values()).filter(s -> s.name().equalsIgnoreCase(sessionName)).findFirst().orElseThrow();
        return bingoCardService.getStatementsForWeekend(weekendId, session);
    }

    public void closeSession(List<VerifiedStatement> verifiedStatements, Long weekendId, Session session) {
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
