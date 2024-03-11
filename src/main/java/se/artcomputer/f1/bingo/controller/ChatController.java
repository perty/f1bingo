package se.artcomputer.f1.bingo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public ChatMessageDto send(ChatMessageDto message) {
        return message; // Skicka meddelandet till alla prenumeranter
    }
}
