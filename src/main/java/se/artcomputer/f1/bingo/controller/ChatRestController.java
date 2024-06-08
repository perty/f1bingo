package se.artcomputer.f1.bingo.controller;

import org.springframework.web.bind.annotation.*;
import se.artcomputer.f1.bingo.domain.ChatService;
import se.artcomputer.f1.bingo.entity.ChatMessageEntity;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("chat")
public class ChatRestController {

    private final ChatService chatService;

    public ChatRestController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/messages/{fan}")
    public List<ChatMessageDto> getMessages(@PathVariable Long fan) {
        List<ChatMessageEntity> all = chatService.getAllMessages();
        chatService.setLastReadForFan(fan);
        return all.stream().map(toDto()).toList();
    }

    @GetMapping("/new-messages/{fan}")
    public List<ChatMessageDto> getNewMessagesForFan(@PathVariable Long fan) {
        return chatService.getNewMessagesForFan(fan).stream().map(toDto()).toList();
    }

    @PostMapping("/lastRead/{fan}")
    public void setLastReadForFan(@PathVariable Long fan) {
        chatService.setLastReadForFan(fan);
    }

    private static Function<ChatMessageEntity, ChatMessageDto> toDto() {
        return e -> new ChatMessageDto(e.getTimestamp(), e.getMessage(), e.getFan());
    }

}
