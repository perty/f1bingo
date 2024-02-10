package se.artcomputer.f1.bingo.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import se.artcomputer.f1.bingo.domain.AdminService;
import se.artcomputer.f1.bingo.entity.Statement;

import java.util.List;

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
        System.out.println("loginData = " + loginData.getFirst("pin-code"));
        return ResponseEntity.ok("OK");
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
