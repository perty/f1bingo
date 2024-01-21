package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointsService {

    public StandingsDto getStandings() {
        return new StandingsDto(List.of(
                new StandingsDto.FanScore(1,"Anders", 3),
                new StandingsDto.FanScore(1, "Bertil", 3),
                new StandingsDto.FanScore(3, "Ceasar", 2)

        ));
    }
}
