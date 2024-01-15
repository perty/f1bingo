package se.artcomputer.f1.bingo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
