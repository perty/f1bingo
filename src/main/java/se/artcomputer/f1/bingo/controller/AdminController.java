package se.artcomputer.f1.bingo.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import se.artcomputer.f1.bingo.domain.AdminService;
import se.artcomputer.f1.bingo.entity.Statement;

import java.util.List;

import static se.artcomputer.f1.bingo.domain.AdminService.AUTH_COOKIE;

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
    public ResponseEntity<String> login(HttpServletResponse response, @RequestParam MultiValueMap<String, String> loginData) {
        String pinCode = loginData.getFirst("pin-code");
        if(pinCode == null || !adminService.login(pinCode)) {
            return ResponseEntity.status(401).body("Felaktig pinkod");
        }
        Cookie cookie = new Cookie(AUTH_COOKIE, adminService.getCookieValue(pinCode));
        cookie.setMaxAge(24 * 60 * 60); // Sätt giltighetstiden för cookien (t.ex. 24 timmar)
        cookie.setHttpOnly(true); // Gör cookien säkrare genom att göra den otillgänglig för klient-sidans JavaScript
        cookie.setPath("/"); // Gör cookien tillgänglig för hela domänen

        // Lägg till cookien i svaret
        response.addCookie(cookie);

        return ResponseEntity.ok("Inloggad");
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
