package se.artcomputer.f1.bingo.controller;

import java.util.Date;

public record DriverDto(
            String code,
            int number,
            String numberClass,
            String name,
            String nationality,
            String flagImage,
            int length,
            Date born
    ) {}
