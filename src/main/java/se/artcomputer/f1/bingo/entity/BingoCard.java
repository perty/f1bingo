package se.artcomputer.f1.bingo.entity;

import jakarta.persistence.*;
import se.artcomputer.f1.bingo.domain.Session;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bingo_card")
public class BingoCard {
    public static final int ROWS = 4;
    public static final int COLS = 4;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session")
    @Enumerated(EnumType.STRING)
    private Session session;

    @OneToMany(mappedBy = "bingoCard", cascade = CascadeType.ALL)
    private Set<BingoCardStatement> statements = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "weekend_palette")
    private WeekendPalette weekendPalette;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Set<BingoCardStatement> getBingoCardStatements() {
        return statements;
    }

    public void setBingoCardStatements(Set<BingoCardStatement> statements) {
        this.statements = statements;
    }

    public void setWeekendPalette(WeekendPalette weekendPalette) {
        this.weekendPalette = weekendPalette;
    }

    public WeekendPalette getWeekendPalette() {
        return weekendPalette;
    }
}
