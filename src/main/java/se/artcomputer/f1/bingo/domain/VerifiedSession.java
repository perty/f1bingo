package se.artcomputer.f1.bingo.domain;

import java.util.List;

public class VerifiedSession {
    private Long weekendId;
    private Session session;
    private List<Long> statements;

    public void setWeekendId(Long weekendId) {
        this.weekendId = weekendId;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void add(long statementId) {
        statements.add(statementId);
    }
}
