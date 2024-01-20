package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.entity.BingoCardStatement;

import java.util.Arrays;
import java.util.List;

@Service
public class VerifyService {
    private final BingoCardService bingoCardService;

    public VerifyService(BingoCardService bingoCardService) {
        this.bingoCardService = bingoCardService;
    }

    public List<BingoCardStatement> getCheckStatements(Long weekendId, String sessionName) {
        Session session = Arrays.stream(Session.values()).filter(s -> s.name().equalsIgnoreCase(sessionName)).findFirst().orElseThrow();
        return bingoCardService.getStatementsForWeekend(weekendId, session);
    }

    public void closeSession(List<VerifiedStatement> verifiedStatements, Long weekendId, Session session) {
        VerifiedSession verifiedSession = new VerifiedSession();
        verifiedSession.setWeekendId(weekendId);
        verifiedSession.setSession(session);
        for (VerifiedStatement verifiedStatement : verifiedStatements) {
            if (verifiedStatement.verified()) {
                verifiedSession.add(verifiedStatement.id());
            }
        }
    }
}
