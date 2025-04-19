package se.artcomputer.f1.bingo.domain;

import java.time.Instant;

public record GpSessionEvent(
    long id,
    String title,
    String description,
    Instant startTime,
    Instant endTime
) {
}
