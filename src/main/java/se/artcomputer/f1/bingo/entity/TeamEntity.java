package se.artcomputer.f1.bingo.entity;

import jakarta.persistence.*;
import se.artcomputer.f1.bingo.domain.Country;
import se.artcomputer.f1.bingo.domain.Engine;
import se.artcomputer.f1.bingo.domain.TeamCode;

@Entity
@Table(name = "team")
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false, name = "code")
    @Enumerated(EnumType.STRING)
    private TeamCode code;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "official_name")
    private String officialName;

    @Column(name = "nationality")
    @Enumerated(EnumType.STRING)
    private Country nationality;

    @Column(name = "engine")
    @Enumerated(EnumType.STRING)
    private Engine engine;

    @Column(name = "team_chief")
    private String teamChief;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TeamCode getCode() {
        return code;
    }

    public void setCode(TeamCode code) {
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engineUnit) {
        this.engine = engineUnit;
    }

    public String getTeamChief() {
        return teamChief;
    }

    public void setTeamChief(String teamChief) {
        this.teamChief = teamChief;
    }
}
