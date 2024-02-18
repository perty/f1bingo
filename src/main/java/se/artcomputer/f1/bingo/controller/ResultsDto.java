package se.artcomputer.f1.bingo.controller;

import java.util.List;

public record ResultsDto(List<String> races,
                         List<ResultFanDto> fans) {
}
