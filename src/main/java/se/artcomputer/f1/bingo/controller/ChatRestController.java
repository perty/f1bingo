package se.artcomputer.f1.bingo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.artcomputer.f1.bingo.domain.ChatService;
import se.artcomputer.f1.bingo.entity.ChatMessageEntity;

import java.util.List;

@RestController
@RequestMapping("chat")
public class ChatRestController {

    private final ChatService chatService;

    public ChatRestController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public List<ChatMessageDto> getMessages() {
        List<ChatMessageEntity> all = chatService.getAllMessages();
        return all.stream().map(e -> new ChatMessageDto(e.getTimestamp(), e.getMessage())).toList();
    }
}
