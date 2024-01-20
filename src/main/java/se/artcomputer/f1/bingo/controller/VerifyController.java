package se.artcomputer.f1.bingo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import se.artcomputer.f1.bingo.domain.Session;
import se.artcomputer.f1.bingo.domain.VerifiedStatement;
import se.artcomputer.f1.bingo.domain.VerifyService;
import se.artcomputer.f1.bingo.entity.BingoCardStatement;
import se.artcomputer.f1.bingo.entity.Statement;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("verify")
public class VerifyController {

    public static final String STATEMENT_ID = "statementId";
    public static final String WEEKEND_ID = "weekendId";
    public static final String SESSION = "session";
    private final VerifyService verifyService;

    public VerifyController(VerifyService verifyService) {
        this.verifyService = verifyService;
    }

    @GetMapping("/weekend/{weekendId}/session/{sessionName}")
    public List<VerifyDto> getCheckStatements(@PathVariable("weekendId") Long weekendId,
                                              @PathVariable("sessionName") String sessionName) {
        return verifyService.getCheckStatements(weekendId, sessionName).stream().map(this::toDto).toList();
    }

    @PostMapping(path = "/close", consumes = {"application/x-www-form-urlencoded"})
    public ResponseEntity<String> closeSession(@CookieValue(name = "session", required = false) String sessionCookie,
                                               @RequestParam MultiValueMap<String, String> statementMap) throws URISyntaxException {
        Long weekendId = statementMap.entrySet().stream()
                .filter(e -> e.getKey().startsWith(WEEKEND_ID))
                .map(e -> Long.parseLong(e.getValue().get(0)))
                .findFirst()
                .orElseThrow();
        Session session = statementMap.entrySet().stream()
                .filter(e -> e.getKey().startsWith(SESSION))
                .map(e -> e.getValue().get(0))
                .map(Session::valueOf)
                .findFirst()
                .orElseThrow();
        List<VerifiedStatement> list = statementMap.entrySet().stream()
                .filter(e -> e.getKey().startsWith(STATEMENT_ID))
                .map(e -> new VerifiedStatement(parseStatementId(e.getKey()), Boolean.parseBoolean(e.getValue().get(0))))
                .toList();
        verifyService.closeSession(list, weekendId, session);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI("/verify.html?weekend=" + weekendId + "&session=" + session.name()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }

    private static long parseStatementId(String key) {
        return Long.parseLong(key.substring(STATEMENT_ID.length()));
    }

    private VerifyDto toDto(BingoCardStatement bingoCardStatement) {
        Statement statement = bingoCardStatement.getStatement();
        return new VerifyDto(statement.getId(), statement.getText());
    }
}
