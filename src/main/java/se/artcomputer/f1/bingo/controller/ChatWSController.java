package se.artcomputer.f1.bingo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import se.artcomputer.f1.bingo.controller.util.GetUserDetails;
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
    public ChatMessageDto send(ChatMessageRequest message) {
        Optional<ChatMessageEntity> saved = GetUserDetails.doIfLoggedIn(userDetails -> chatService.save(message.message(), userDetails), Optional.empty());
        if (saved.isEmpty()) {
            return new ChatMessageDto(ChatMessageType.MESSAGE, Timestamp.from(Instant.now()), "Could not save message", 0);
        }
        ChatMessageEntity savedEntity = saved.get();
        return new ChatMessageDto(ChatMessageType.MESSAGE, savedEntity.getTimestamp(), savedEntity.getMessage(), savedEntity.getFan());
    }
}
