package se.artcomputer.f1.bingo.domain;

import java.util.List;

public record StandingsDto(List<FanScore> fanScores) {

    public record FanScore(int position, String fanName, Long score) {
    }
}
