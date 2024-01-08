package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.entity.RaceWeekend;
import se.artcomputer.f1.bingo.repository.RaceWeekendRepository;

import java.util.List;

@Service
public class RaceService {
    private final RaceWeekendRepository raceWeekendRepository;

    public RaceService(RaceWeekendRepository raceWeekendRepository) {
        this.raceWeekendRepository = raceWeekendRepository;
    }

    public List<RaceWeekend> getRaceWeekends() {
        return raceWeekendRepository.findAll();
    }
}
