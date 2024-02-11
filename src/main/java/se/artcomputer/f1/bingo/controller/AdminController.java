package se.artcomputer.f1.bingo.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import se.artcomputer.f1.bingo.domain.AdminService;
import se.artcomputer.f1.bingo.entity.Statement;

import java.util.List;

import static se.artcomputer.f1.bingo.domain.AdminService.AUTH_COOKIE;
import static se.artcomputer.f1.bingo.exception.ExceptionHandling.REDIRECT_URL;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/statements")
    public List<StatementDto> getStatements() {
        return adminService.getStatements().stream().map(this::toDto).toList();
    }

    @PostMapping(path = "/login", consumes = {"application/x-www-form-urlencoded"})
    public ResponseEntity<String> login(@RequestParam MultiValueMap<String, String> loginData) {
        String pinCode = loginData.getFirst("pin-code");
        if (pinCode == null || !adminService.login(pinCode)) {
            return ResponseEntity.status(401).body("Felaktig pinkod");
        }
        ResponseCookie responseCookie = ResponseCookie.from(AUTH_COOKIE, adminService.getCookieValue(pinCode))
                .maxAge(24 * 60 * 60)
                .httpOnly(true)
                .path("/")
                .build();

        String url = loginData.getFirst(REDIRECT_URL);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, url)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }

    private StatementDto toDto(Statement statement) {
        return new StatementDto(statement.getText(),
                statement.getCategory() == null ? "INGEN" : statement.getCategory().name(),
                statement.isEnabled(),
                statement.isRace(),
                statement.isQualifying(),
                statement.isSprintShootout(),
                statement.isSprintRace());
    }
}
