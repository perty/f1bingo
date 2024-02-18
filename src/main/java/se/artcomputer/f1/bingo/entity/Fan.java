package se.artcomputer.f1.bingo.entity;

import jakarta.persistence.*;
import se.artcomputer.f1.bingo.domain.FanName;

@Entity
public class Fan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public FanName getFanName() {
        return new FanName(name);
    }

    public void setName(String name) {
        this.name = name;
    }
}
