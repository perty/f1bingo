package se.artcomputer.f1.bingo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.artcomputer.f1.bingo.domain.VerifyService;
import se.artcomputer.f1.bingo.entity.BingoCardStatement;

import java.util.List;

@RestController
@RequestMapping("verify")
public class VerifyController {

    private final VerifyService verifyService;

    public VerifyController(VerifyService verifyService) {
        this.verifyService = verifyService;
    }

    @GetMapping("/weekend/{weekendId}/session/{sessionName}")
    public List<VerifyDto> getCheckStatements(@PathVariable("weekendId") Long weekendId,
                                              @PathVariable("sessionName") String sessionName) {
        return verifyService.getCheckStatements(weekendId, sessionName).stream().map(this::toDto).toList();
    }

    private VerifyDto toDto(BingoCardStatement bingoCardStatement) {
        return new VerifyDto(bingoCardStatement.getStatement().getText());
    }
}
