package se.artcomputer.f1.bingo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import se.artcomputer.f1.bingo.domain.ChatService;
import se.artcomputer.f1.bingo.entity.ChatMessageEntity;

@Controller
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public ChatMessageDto send(ChatMessageRequest message) {
        ChatMessageEntity saved = chatService.save(message.message());
        return new ChatMessageDto(saved.getTimestamp(), message.message());
    }
}
