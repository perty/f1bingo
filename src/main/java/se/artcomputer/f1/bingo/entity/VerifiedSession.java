package se.artcomputer.f1.bingo.entity;

import jakarta.persistence.*;
import se.artcomputer.f1.bingo.domain.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Entity
@Table(name = "verified_session")
public class VerifiedSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "created")
    private Date created = new Date();

    @ManyToOne
    @JoinColumn(name = "race_weekend")
    private RaceWeekend raceWeekend;

    @Column(name = "session")
    @Enumerated(EnumType.STRING)
    private Session session;

    @OneToMany(mappedBy = "verifiedSession", cascade = CascadeType.REMOVE)
    private List<VerifiedStatementEntity> statements = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWeekend(RaceWeekend raceWeekend) {
        this.raceWeekend = raceWeekend;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void add(Statement statement) {
        VerifiedStatementEntity verifiedStatementEntity = new VerifiedStatementEntity();
        verifiedStatementEntity.setVerifiedSession(this);
        verifiedStatementEntity.setStatement(statement);
        statements.add(verifiedStatementEntity);
    }

    public Date getCreated() {
        return created;
    }

    public List<VerifiedStatementEntity> getStatements() {
        return statements;
    }

    public RaceWeekend getRaceWeekend() {
        return raceWeekend;
    }

    public Session getSession() {
        return session;
    }
}
