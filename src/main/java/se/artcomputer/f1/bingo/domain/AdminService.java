package se.artcomputer.f1.bingo.domain;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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
        return statementRepository.findAll(Sort.by("id"));
    }
}
