package se.artcomputer.f1.bingo.controller;

import java.util.Date;

public record ChatMessageDto(Date timestamp, String message, long fan) {
}
