package se.artcomputer.f1.bingo.entity;

import jakarta.persistence.*;
import se.artcomputer.f1.bingo.domain.RaceWeekendType;

@Entity
@Table(name = "race_weekend")
public class RaceWeekend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "weekend_type")
    @Enumerated(EnumType.STRING)
    private RaceWeekendType type;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RaceWeekendType getType() {
        return type;
    }

    public void setType(RaceWeekendType type) {
        this.type = type;
    }
}
