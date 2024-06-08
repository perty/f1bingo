package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.controller.ChatMessageDto;
import se.artcomputer.f1.bingo.entity.ChatMessageEntity;
import se.artcomputer.f1.bingo.entity.Fan;
import se.artcomputer.f1.bingo.entity.RaceWeekend;
import se.artcomputer.f1.bingo.repository.ChatRepository;
import se.artcomputer.f1.bingo.repository.FanRepository;
import se.artcomputer.f1.bingo.repository.RaceWeekendRepository;

import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final FanRepository fanRepository;
    private final RaceWeekendRepository raceWeekendRepository;

    public ChatService(ChatRepository chatRepository, FanRepository fanRepository, RaceWeekendRepository raceWeekendRepository) {
        this.chatRepository = chatRepository;
        this.fanRepository = fanRepository;
        this.raceWeekendRepository = raceWeekendRepository;
    }

    public ChatMessageEntity save(String messageString, long fan) {
        ChatMessageEntity message = new ChatMessageEntity();
        message.setMessage(messageString);
        message.setTimestamp(Date.from(Instant.now()));
        message.setFan(fan);
        return chatRepository.save(message);
    }

    public List<ChatMessageDto> getAllMessages() {
        Stream<ChatMessageDto> started = raceWeekendRepository.findByStartDateBefore(Date.from(Instant.now())).stream()
                .map(this::gpDto);
        Stream<ChatMessageDto> messageDtos = chatRepository.findAll().stream()
                .map(this::messageDto);
        return Stream.concat(started, messageDtos).sorted(Comparator.comparing(ChatMessageDto::timestamp)).toList();
    }

    public List<ChatMessageDto> getNewMessagesForFan(Long fanId) {
        Optional<Fan> fan = fanRepository.findById(fanId);
        if (fan.isPresent()) {
            return chatRepository.findByTimestampGreaterThan(fan.get().getLastRead()).stream().map(this::messageDto).toList();
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

    private ChatMessageDto messageDto(ChatMessageEntity e) {
        return new ChatMessageDto(ChatMessageType.MESSAGE, e.getTimestamp(), e.getMessage(), e.getFan());
    }

    private ChatMessageDto gpDto(RaceWeekend raceWeekend) {
        return new ChatMessageDto(ChatMessageType.GP, raceWeekend.getStartDate(), raceWeekend.getName(), 0);
    }
}
