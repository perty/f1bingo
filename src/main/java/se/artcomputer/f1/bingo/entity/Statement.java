package se.artcomputer.f1.bingo.entity;

import jakarta.persistence.*;
import se.artcomputer.f1.bingo.domain.StatementCategory;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @OneToMany(mappedBy = "statement", cascade = CascadeType.ALL)
    private Set<BingoCardStatement> bingoCards = new HashSet<>();

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private StatementCategory category;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "race")
    private boolean race;

    @Column(name = "qualifying")
    private boolean qualifying;

    @Column(name = "sprint_shootout")
    private boolean sprintShootout;

    @Column(name = "sprint_race")
    private boolean sprintRace;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
