package se.artcomputer.f1.bingo.controller;

import se.artcomputer.f1.bingo.domain.RaceName;

import java.util.List;

public record ResultsDto(List<RaceName> races,
                         List<ResultFanDto> fans) {
}
