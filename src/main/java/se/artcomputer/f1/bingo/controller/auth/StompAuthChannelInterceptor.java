package se.artcomputer.f1.bingo.controller.auth;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class StompAuthChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor acc = StompHeaderAccessor.wrap(message);

        if (acc.getCommand() == null) return message;

        if (acc.getCommand() == StompCommand.SEND || acc.getCommand() == StompCommand.SUBSCRIBE) {
            Object userId = acc.getSessionAttributes() != null
                    ? acc.getSessionAttributes().get("userId")
                    : null;

            if (userId == null) {
                throw new RuntimeException("Not authenticated");
            }
        }
        return message;
    }
}
