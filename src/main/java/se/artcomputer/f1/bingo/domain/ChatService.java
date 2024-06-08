package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.entity.ChatMessageEntity;
import se.artcomputer.f1.bingo.entity.Fan;
import se.artcomputer.f1.bingo.repository.ChatRepository;
import se.artcomputer.f1.bingo.repository.FanRepository;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final FanRepository fanRepository;

    public ChatService(ChatRepository chatRepository, FanRepository fanRepository) {
        this.chatRepository = chatRepository;
        this.fanRepository = fanRepository;
    }

    public ChatMessageEntity save(String messageString, long fan) {
        ChatMessageEntity message = new ChatMessageEntity();
        message.setMessage(messageString);
        message.setTimestamp(Date.from(Instant.now()));
        message.setFan(fan);
        return chatRepository.save(message);
    }

    public List<ChatMessageEntity> getAllMessages() {
        return chatRepository.findAll();
    }

    public List<ChatMessageEntity> getNewMessagesForFan(Long fanId) {
        Optional<Fan> fan = fanRepository.findById(fanId);
        if (fan.isPresent()) {
            return chatRepository.findByTimestampGreaterThan(fan.get().getLastRead());
        }
        return Collections.emptyList();
    }

    public void setLastReadForFan(Long fanId) {
        Optional<Fan> fan = fanRepository.findById(fanId);
        if (fan.isPresent()) {
            fan.get().setLastRead(Date.from(Instant.now()));
            fanRepository.save(fan.get());
        }
    }
}
