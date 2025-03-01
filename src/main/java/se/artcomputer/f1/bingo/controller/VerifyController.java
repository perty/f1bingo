package se.artcomputer.f1.bingo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import se.artcomputer.f1.bingo.domain.Session;
import se.artcomputer.f1.bingo.domain.VerifiedStatement;
import se.artcomputer.f1.bingo.domain.VerifyService;
import se.artcomputer.f1.bingo.entity.BingoCardStatement;
import se.artcomputer.f1.bingo.entity.Statement;
import se.artcomputer.f1.bingo.entity.VerifiedSession;
import se.artcomputer.f1.bingo.entity.VerifiedStatementEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public CheckStatementsDto getCheckStatements(@PathVariable("weekendId") Long weekendId,
                                                 @PathVariable("sessionName") String sessionName) {

        Optional<VerifiedSession> verifiedSession = verifyService.getVerifiedSession(weekendId, sessionName);
        if (verifiedSession.isPresent()) {
            Set<VerifyStatementDto> statements = verifiedSession.get().getStatements().stream().map(this::toDto).collect(Collectors.toSet());
            return new CheckStatementsDto(statements, verifiedSession.map(VerifiedSession::getCreated));
        } else {
            Set<VerifyStatementDto> statements = verifyService.getCheckStatements(weekendId, sessionName).stream().map(this::toDto).collect(Collectors.toSet());
            return new CheckStatementsDto(statements, Optional.empty());
        }
    }

    @PostMapping(path = "/toggle-close", consumes = {"application/x-www-form-urlencoded"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> toggleCloseSession(@RequestParam MultiValueMap<String, String> statementMap) throws URISyntaxException {
        Long weekendId = statementMap.entrySet().stream()
                .filter(e -> e.getKey().startsWith(WEEKEND_ID))
                .map(e -> Long.parseLong(e.getValue().getFirst()))
                .findFirst()
                .orElseThrow();
        Session session = statementMap.entrySet().stream()
                .filter(e -> e.getKey().startsWith(SESSION))
                .map(e -> e.getValue().getFirst())
                .map(Session::valueOf)
                .findFirst()
                .orElseThrow();
        List<VerifiedStatement> list = statementMap.entrySet().stream()
                .filter(e -> e.getKey().startsWith(STATEMENT_ID))
                .map(e -> new VerifiedStatement(parseStatementId(e.getKey()), e.getValue().getFirst().equals("on")))
                .toList();
        verifyService.toggleCloseSession(list, weekendId, session);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI("/verify.html?weekend=" + weekendId + "&session=" + session.name()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }

    private static long parseStatementId(String key) {
        return Long.parseLong(key.substring(STATEMENT_ID.length()));
    }

    private VerifyStatementDto toDto(BingoCardStatement bingoCardStatement) {
        Statement statement = bingoCardStatement.getStatement();
        return new VerifyStatementDto(statement.getId(), statement.getText());
    }

    private VerifyStatementDto toDto(VerifiedStatementEntity verifiedStatementEntity) {
        Statement statement = verifiedStatementEntity.getStatement();
        return new VerifyStatementDto(statement.getId(), statement.getText());
    }
}
