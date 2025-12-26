package se.artcomputer.f1.bingo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.artcomputer.f1.bingo.entity.RememberMeToken;

public interface RememberMeRepository extends JpaRepository<RememberMeToken, String> {
}
