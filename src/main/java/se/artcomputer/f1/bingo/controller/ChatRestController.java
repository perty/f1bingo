package se.artcomputer.f1.bingo.controller;

import org.springframework.web.bind.annotation.*;
import se.artcomputer.f1.bingo.domain.ChatService;

import java.util.List;

@RestController
@RequestMapping("chat")
public class ChatRestController {

    private final ChatService chatService;

    public ChatRestController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/messages/{fan}")
    public List<ChatMessageDto> getMessages(@PathVariable Long fan) {
        List<ChatMessageDto> allMessages = chatService.getAllMessages();
        chatService.setLastReadForFan(fan);
        return allMessages;
    }

    @GetMapping("/new-messages/{fan}")
    public List<ChatMessageDto> getNewMessagesForFan(@PathVariable Long fan) {
        return chatService.getNewMessagesForFan(fan);
    }

    @PostMapping("/lastRead/{fan}")
    public void setLastReadForFan(@PathVariable Long fan) {
        chatService.setLastReadForFan(fan);
    }

}
