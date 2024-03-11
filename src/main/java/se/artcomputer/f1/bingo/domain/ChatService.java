package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.entity.ChatMessageEntity;
import se.artcomputer.f1.bingo.repository.ChatRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public ChatMessageEntity save(String messageString) {
        ChatMessageEntity message = new ChatMessageEntity();
        message.setMessage(messageString);
        message.setTimestamp(Date.from(Instant.now()));
        return chatRepository.save(message);
    }

    public List<ChatMessageEntity> getAllMessages() {
        return chatRepository.findAll();
    }
}
