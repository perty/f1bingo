package se.artcomputer.f1.bingo.controller;

import se.artcomputer.f1.bingo.domain.ChatMessageType;

import java.util.Date;

public record ChatMessageDto(ChatMessageType messageType, Date timestamp, String message, long fan) {
}
