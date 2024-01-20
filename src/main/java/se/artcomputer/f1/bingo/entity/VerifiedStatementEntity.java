package se.artcomputer.f1.bingo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "verified_statement")
public class VerifiedStatementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "verified_session")
    private VerifiedSession verifiedSession;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statement")
    private Statement statement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVerifiedSession(VerifiedSession verifiedSession) {
        this.verifiedSession = verifiedSession;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
}
