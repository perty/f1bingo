package se.artcomputer.f1.bingo.controller;

import java.util.List;

public record ResultFanDto(String fanName,
                           List<Integer> fanScores) {
}
