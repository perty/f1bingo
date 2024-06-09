package se.artcomputer.f1.bingo.controller;

import se.artcomputer.f1.bingo.domain.FanName;

import java.util.List;

public record ResultFanDto(FanName fanName,
                           List<Long> fanScores,
                           long totalScore) {
}
