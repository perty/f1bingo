package se.artcomputer.f1.bingo.domain;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.entity.RaceWeekend;
import se.artcomputer.f1.bingo.repository.RaceWeekendRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class RaceService {
    private static final Calendar CALENDAR = Calendar.getInstance();
    private final RaceWeekendRepository raceWeekendRepository;

    public RaceService(RaceWeekendRepository raceWeekendRepository) {
        this.raceWeekendRepository = raceWeekendRepository;
    }

    public List<RaceWeekend> getRaceWeekends() {
        return getRaceWeekends(2025);
    }

    public List<RaceWeekend> getRaceWeekends(int year) {
        List<RaceWeekend> allRaces = raceWeekendRepository.findAll(Sort.by("startDate"));
        return allRaces.stream()
                .filter(r -> getYearFromDate(r.getStartDate()) == year)
                .toList();
    }

    public RaceWeekend getRaceWeekend(Long weekendId) {
        return raceWeekendRepository.findById(weekendId).orElseThrow();
    }

    private static int getYearFromDate(Date date) {
        CALENDAR.setTime(date);
        return CALENDAR.get(Calendar.YEAR);
    }

    public Stream<RaceWeekend> findByCountry(String country) {
        return raceWeekendRepository.findByCountry(country).stream();
    }
}
