package se.artcomputer.f1.bingo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.artcomputer.f1.bingo.entity.ChatMessageEntity;

public interface ChatRepository extends JpaRepository<ChatMessageEntity, Long> {
}
