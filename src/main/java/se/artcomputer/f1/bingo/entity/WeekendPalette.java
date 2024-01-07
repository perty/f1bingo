package se.artcomputer.f1.bingo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "weekend_palette")
public class WeekendPalette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "weekend_palette")
    private List<BingoCard> bingoCards;

    @ManyToOne
    @JoinColumn(name = "fan")
    private Fan fan;

    @ManyToOne
    @JoinColumn(name = "weekend")
    private RaceWeekend raceWeekend;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<BingoCard> getBingoCards() {
        return bingoCards;
    }

    public void setBingoCards(List<BingoCard> bingoCards) {
        this.bingoCards = bingoCards;
    }

    public Fan getFan() {
        return fan;
    }

    public void setFan(Fan fan) {
        this.fan = fan;
    }

    public RaceWeekend getRaceWeekend() {
        return raceWeekend;
    }

    public void setRaceWeekend(RaceWeekend raceWeekend) {
        this.raceWeekend = raceWeekend;
    }
}
