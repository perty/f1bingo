package se.artcomputer.f1.bingo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.artcomputer.f1.bingo.controller.auth.FanDetails;
import se.artcomputer.f1.bingo.controller.auth.GetUserDetails;
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

    @GetMapping("/messages")
    public List<ChatMessageDto> getMessages() {
        return GetUserDetails.doIfLoggedIn(chatService::getAllMessages, Collections.emptyList());
    }

    @GetMapping("/new-messages")
    public List<ChatMessageDto> getNewMessagesForFan() {
        return GetUserDetails.doIfLoggedIn(chatService::getNewMessagesForFan, Collections.emptyList());
    }

    @GetMapping("/fan-ids")
    public List<FanIdName> getFanIds() {
        Optional<FanDetails> userDetails = GetUserDetails.getLoggedInUserDetails();
        if(userDetails.isPresent()) {
            return chatService.getFanIdNames();
        }
        return Collections.emptyList();
    }

    @PostMapping("/lastRead")
    public void setLastReadForFan() {
        Optional<FanDetails> userDetails = GetUserDetails.getLoggedInUserDetails();
        userDetails.ifPresent(chatService::setLastReadForFan);
    }

}
