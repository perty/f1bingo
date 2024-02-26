package se.artcomputer.f1.bingo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import se.artcomputer.f1.bingo.controller.util.GetCookie;
import se.artcomputer.f1.bingo.domain.*;
import se.artcomputer.f1.bingo.entity.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Optional;

@RestController
@RequestMapping("reset")
public class ResetController {

    public static final String WEEKEND_ID = "weekendId";
    public static final String SESSION = "session";
    private final VerifyService verifyService;
    private final RaceService raceService;
    private final AdminService adminService;
    private final BingoCardService bingoCardService;

    public ResetController(VerifyService verifyService, RaceService raceService, AdminService adminService, BingoCardService bingoCardService) {
        this.verifyService = verifyService;
        this.raceService = raceService;
        this.adminService = adminService;
        this.bingoCardService = bingoCardService;
    }

    @GetMapping("/weekend/{weekendId}/session/{sessionName}")
    public ResetConfirmData confirmData(@PathVariable("weekendId") Long weekendId,
                                        @PathVariable("sessionName") String sessionName) {
        Optional<VerifiedSession> optional = verifyService.getVerifiedSession(weekendId, sessionName);
        if (optional.isPresent()) {
            VerifiedSession v = optional.get();
            return new ResetConfirmData(ResetState.CLOSED, v.getRaceWeekend().nameWithDates(), v.getSession(), 0L);
        }
        RaceWeekend raceWeekend = raceService.getRaceWeekend(weekendId);
        Session session = Session.valueOf(sessionName.toUpperCase());
        long withClicks = bingoCardService.countBingoCardsWithClicks(raceWeekend, session);
        return new ResetConfirmData(ResetState.OPEN, raceWeekend.nameWithDates(), session, withClicks);
    }

    @PostMapping(path = "/confirm-reset", consumes = {"application/x-www-form-urlencoded"})
    public ResponseEntity<String> confirmReset(@RequestHeader HttpHeaders headers,
                                               @RequestParam MultiValueMap<String, String> statementMap) throws URISyntaxException {
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
        adminService.checkLogin(GetCookie.getCookie(headers), returnUrl(weekendId, session));
        RaceWeekend raceWeekend = raceService.getRaceWeekend(weekendId);
        bingoCardService.resetBingoCards(raceWeekend, session);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI("/reset.html?weekend=" + weekendId + "&session=" + session.name()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }

    private String returnUrl(Long weekendId, Session session) {
        return URLEncoder.encode("/reset.html?weekend=" + weekendId + "&session=" + session.name(),
                java.nio.charset.StandardCharsets.UTF_8);
    }
}
