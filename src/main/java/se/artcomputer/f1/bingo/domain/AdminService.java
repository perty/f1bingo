package se.artcomputer.f1.bingo.domain;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.controller.StatementUpdateRequest;
import se.artcomputer.f1.bingo.entity.Statement;
import se.artcomputer.f1.bingo.repository.StatementRepository;

import java.util.Collection;

@Service
public class AdminService {
    private final StatementRepository statementRepository;

    public AdminService(StatementRepository statementRepository) {
        this.statementRepository = statementRepository;
    }

    public Collection<Statement> getStatements() {
        return statementRepository.findAll(Sort.by("id").reverse());
    }

    public Statement setStatement(long id, StatementUpdateRequest statementUpdateRequest) {
        Statement statement = statementRepository.findById(id).orElseThrow();
        statement.setEnabled(statementUpdateRequest.enabled());
        statement.setText(statementUpdateRequest.text());
        statement.setCategory(StatementCategory.valueOf(statementUpdateRequest.category()));
        statement.setSprintShootout(statementUpdateRequest.sprintShootout());
        statement.setSprintRace(statementUpdateRequest.sprintRace());
        statement.setQualifying(statementUpdateRequest.qualifying());
        statement.setRace(statementUpdateRequest.race());
        return statementRepository.save(statement);
    }

    public void newStatement(String text, StatementCategory category) {
        Statement entity = new Statement();
        entity.setText(text);
        entity.setCategory(category);
        entity.setEnabled(true);
        statementRepository.save(entity);
    }
}
