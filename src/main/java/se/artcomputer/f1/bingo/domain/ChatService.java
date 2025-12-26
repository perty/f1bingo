package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.controller.ChatMessageDto;
import se.artcomputer.f1.bingo.controller.FanIdName;
import se.artcomputer.f1.bingo.controller.auth.FanDetails;
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

    public Optional<ChatMessageEntity> save(String messageString, FanDetails FanDetails) {
        Optional<Fan> fan = fanRepository.findByName(FanDetails.name());
        if (fan.isPresent()) {
            ChatMessageEntity message = new ChatMessageEntity();
            message.setMessage(messageString);
            message.setTimestamp(Date.from(Instant.now()));
            message.setFan(fan.get().getId());
            setLastReadForFan(fan.get());
            return Optional.of(chatRepository.save(message));
        }
        return Optional.empty();
    }

    public List<ChatMessageDto> getAllMessages(FanDetails FanDetails) {
        Date now = Date.from(Instant.now());
        Stream<ChatMessageDto> started = raceWeekendRepository.findByStartDateBefore(now).stream()
                .map(this::gpDto);
        Stream<ChatMessageDto> messageDtos = chatRepository.findAll().stream()
                .map(this::messageDto);
        setLastReadForFan(FanDetails);
        return Stream.concat(started, messageDtos).sorted(Comparator.comparing(ChatMessageDto::timestamp)).toList();
    }

    public List<ChatMessageDto> getNewMessagesForFan(FanDetails FanDetails) {
        Optional<Fan> optionalFan = fanRepository.findByName(FanDetails.name());
        if (optionalFan.isPresent()) {
            Date lastRead = optionalFan.get().getLastRead();
            return chatRepository.findByTimestampGreaterThan(lastRead).stream().map(this::messageDto).toList();
        }
        return Collections.emptyList();
    }

    public List<FanIdName> getFanIdNames() {
        return fanRepository.findAll().stream().map(f -> new FanIdName(f.getId(), f.getName())).toList();
    }

    public void setLastReadForFan(FanDetails FanDetails) {
        fanRepository.findByName(FanDetails.name()).ifPresent(this::setLastReadForFan);
    }

    private void setLastReadForFan(Fan fan) {
        fan.setLastRead(Date.from(Instant.now()));
        fanRepository.save(fan);
    }

    private ChatMessageDto messageDto(ChatMessageEntity e) {
        return new ChatMessageDto(ChatMessageType.MESSAGE, e.getTimestamp(), e.getMessage(), e.getFan());
    }

    private ChatMessageDto gpDto(RaceWeekend raceWeekend) {
        return new ChatMessageDto(ChatMessageType.GP, raceWeekend.getStartDate(), raceWeekend.getName(), 0);
    }
}
