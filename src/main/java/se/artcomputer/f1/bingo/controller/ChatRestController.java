package se.artcomputer.f1.bingo.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import se.artcomputer.f1.bingo.controller.util.GetUserDetails;
import se.artcomputer.f1.bingo.domain.ChatService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("chat")
public class ChatRestController {

    private final ChatService chatService;

    public ChatRestController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/messages/{fan}")
    public List<ChatMessageDto> getMessages(@PathVariable Long fan) {
        return GetUserDetails.doIfLoggedIn(chatService::getAllMessages, Collections.emptyList());
    }

    @GetMapping("/new-messages/{fan}")
    public List<ChatMessageDto> getNewMessagesForFan(@PathVariable Long fan) {
        return GetUserDetails.doIfLoggedIn(chatService::getNewMessagesForFan, Collections.emptyList());
    }

    @PostMapping("/lastRead/{fan}")
    public void setLastReadForFan(@PathVariable Long fan) {
        Optional<UserDetails> userDetails = GetUserDetails.getLoggedInUserDetails();
        userDetails.ifPresent(chatService::setLastReadForFan);
    }

}
