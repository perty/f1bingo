package se.artcomputer.f1.bingo.controller;

import java.util.List;

public record TeamDto(
        String displayName,
        String carImage,
        String engineUnit,
        List<DriverDto> drivers,
        String teamNationality,
        String teamNationalityFlag,
        String officialName,
        String teamPrincipal
) {
}
