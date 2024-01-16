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
}
