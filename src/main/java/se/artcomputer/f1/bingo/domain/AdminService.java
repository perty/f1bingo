package se.artcomputer.f1.bingo.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.entity.Statement;
import se.artcomputer.f1.bingo.exception.UnAuthorizedException;
import se.artcomputer.f1.bingo.repository.StatementRepository;

import java.util.Collection;

@Service
public class AdminService {
    private static final Logger LOG = LoggerFactory.getLogger(AdminService.class);
    public static final String AUTH_COOKIE = "f1bingo";
    public static final String ENCRYPTED_TOKEN_VALUE = "encryptedTokenValue";

    private final StatementRepository statementRepository;

    public AdminService(StatementRepository statementRepository) {
        this.statementRepository = statementRepository;
    }

    public Collection<Statement> getStatements() {
        return statementRepository.findAll(Sort.by("id"));
    }

    public void checkSession(String cookie) {
        LOG.info("Checking session for cookie: {}", cookie);
        if(!cookie.startsWith(ENCRYPTED_TOKEN_VALUE)) {
            throw new UnAuthorizedException("Unauthorized");
        }
    }

    public boolean login(String pinCode) {
        return "1234".equals(pinCode);
    }

    public String getCookieValue(String pinCode) {
        return ENCRYPTED_TOKEN_VALUE + pinCode;
    }
}
