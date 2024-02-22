package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.entity.Fan;
import se.artcomputer.f1.bingo.repository.FanRepository;

import java.util.List;

@Service
public class FanService {
    private final FanRepository fanRepository;

    public FanService(FanRepository fanRepository) {
        this.fanRepository = fanRepository;
    }

    public List<Fan> getFans() {
        return fanRepository.findAll();
    }

    public void addFan(String name) {
        Fan fan = new Fan();
        fan.setName(name);
        fanRepository.save(fan);
    }
}
