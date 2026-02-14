package se.artcomputer.f1.bingo.entity;

import jakarta.persistence.*;
import se.artcomputer.f1.bingo.domain.DriverCode;
import se.artcomputer.f1.bingo.domain.TeamCode;

@Entity
@Table(name = "contract")
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "driver")
    @Enumerated(EnumType.STRING)
    private DriverCode driver;

    @Column(name = "team")
    @Enumerated(EnumType.STRING)
    private TeamCode team;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DriverCode getDriver() {
        return driver;
    }

    public void setDriver(DriverCode driver) {
        this.driver = driver;
    }

    public TeamCode getTeam() {
        return team;
    }

    public void setTeam(TeamCode team) {
        this.team = team;
    }
}
