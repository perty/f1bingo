package se.artcomputer.f1.bingo.controller;

import java.util.List;

public record ResultFanDto(se.artcomputer.f1.bingo.domain.FanName fanName,
                           List<Long> fanScores) {
}
