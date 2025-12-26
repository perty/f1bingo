package se.artcomputer.f1.bingo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import se.artcomputer.f1.bingo.domain.ChatMessageType;
import se.artcomputer.f1.bingo.domain.ChatService;
import se.artcomputer.f1.bingo.entity.ChatMessageEntity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Controller
public class ChatWSController {

    private final ChatService chatService;

    public ChatWSController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public ChatMessageDto send(ChatMessageRequest message, SimpMessageHeaderAccessor headers) {
        Long userId = (Long) headers.getSessionAttributes().get("userId");

        Optional<ChatMessageEntity> optionalChatMessage = chatService.save(message.message(), userId);
        if (optionalChatMessage.isEmpty()) {
            return new ChatMessageDto(ChatMessageType.MESSAGE, Timestamp.from(Instant.now()), "Could not save message", 0);
        }
        ChatMessageEntity savedEntity = optionalChatMessage.get();
        return new ChatMessageDto(ChatMessageType.MESSAGE, savedEntity.getTimestamp(), savedEntity.getMessage(), savedEntity.getFan());
    }
}
