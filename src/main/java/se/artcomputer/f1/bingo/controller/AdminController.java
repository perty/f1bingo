package se.artcomputer.f1.bingo.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/statements")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StatementRequest> newStatement(@RequestBody StatementRequest statementRequest) {
        adminService.newStatement(statementRequest.text(), statementRequest.category());
        return ResponseEntity.ok(statementRequest);
    }

    @PostMapping("/statements/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public StatementDto setStatement(@PathVariable long id, @RequestBody StatementUpdateRequest statementUpdateRequest) {
        return toDto(adminService.setStatement(id, statementUpdateRequest));
    }


    private StatementDto toDto(Statement statement) {
        return new StatementDto(
                statement.getId(),
                statement.getText(),
                statement.getCategory().name(),
                statement.isEnabled(),
                statement.isRace(),
                statement.isQualifying(),
                statement.isSprintShootout(),
                statement.isSprintRace());
    }
}
