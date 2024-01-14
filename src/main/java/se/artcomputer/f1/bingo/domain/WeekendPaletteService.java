package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.entity.Fan;
import se.artcomputer.f1.bingo.entity.RaceWeekend;
import se.artcomputer.f1.bingo.entity.WeekendPalette;
import se.artcomputer.f1.bingo.exception.NotFoundException;
import se.artcomputer.f1.bingo.repository.FanRepository;
import se.artcomputer.f1.bingo.repository.RaceWeekendRepository;
import se.artcomputer.f1.bingo.repository.WeekendPaletteRepository;

import java.util.Optional;

@Service
public class WeekendPaletteService {

    private final WeekendPaletteRepository weekendPaletteRepository;
    private final RaceWeekendRepository raceWeekendRepository;
    private final FanRepository fanRepository;
    private final BingoCardService bingoCardService;

    public WeekendPaletteService(WeekendPaletteRepository weekendPaletteRepository, RaceWeekendRepository raceWeekendRepository, FanRepository fanRepository, BingoCardService bingoCardService) {
        this.weekendPaletteRepository = weekendPaletteRepository;
        this.raceWeekendRepository = raceWeekendRepository;
        this.fanRepository = fanRepository;
        this.bingoCardService = bingoCardService;
    }

    public WeekendPalette getWeekendPalette(Long weekendId, Long fanId) {
        RaceWeekend raceWeekend = raceWeekendRepository.findById(weekendId).orElseThrow(() -> new NotFoundException("Weekend"));
        Fan fan = fanRepository.findById(fanId).orElseThrow(() -> new NotFoundException("Fan"));
        Optional<WeekendPalette> byRaceWeekendAndFan = weekendPaletteRepository.findByRaceWeekendAndFan(raceWeekend, fan);
        return byRaceWeekendAndFan.orElseGet(() -> generateWeekendPalette(raceWeekend, fan));
    }

    private WeekendPalette generateWeekendPalette(RaceWeekend raceWeekend, Fan fan) {
        WeekendPalette weekendPalette = new WeekendPalette();
        weekendPalette.setRaceWeekend(raceWeekend);
        weekendPalette.setFan(fan);
        weekendPalette.setBingoCards(bingoCardService.createBingoCards(weekendPalette, raceWeekend));
        return weekendPaletteRepository.save(weekendPalette);
    }

    public void click(int cellId) {
        bingoCardService.click(cellId);
    }
}
