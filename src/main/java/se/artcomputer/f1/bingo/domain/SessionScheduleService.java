package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.entity.RaceWeekend;
import se.artcomputer.f1.bingo.entity.SessionSchedule;
import se.artcomputer.f1.bingo.repository.SessionScheduleRepository;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SessionScheduleService {
    private final RaceService raceService;
    private final SessionScheduleRepository sessionScheduleRepository;

    public SessionScheduleService(RaceService raceService, SessionScheduleRepository sessionScheduleRepository) {
        this.raceService = raceService;
        this.sessionScheduleRepository = sessionScheduleRepository;
    }

    public Optional<Date> findSessionStart(Long weekendId, Session session) {
        RaceWeekend raceWeekend = raceService.getRaceWeekend(weekendId);
        String country = raceWeekend.getCountry();
        Date endDate = Date.from(raceWeekend.getEndDate().toInstant().plus(1, ChronoUnit.DAYS));
        List<SessionSchedule> byLocation = sessionScheduleRepository.findByLocationOrderByStartTime(country)
                .stream()
                .filter(sessionSchedule -> sessionSchedule.getStartTime().before(endDate) &&
                        sessionSchedule.getEndTime().after(raceWeekend.getStartDate()))
                .toList();
        int index = sessionIndex(session);
        if (byLocation.size() > index) {
            return Optional.of(byLocation.get(index).getStartTime());
        }
        return Optional.empty();
    }

    private static int sessionIndex(Session session) {
        return switch (session) {
            case RACE -> 4;
            case SPRINT_RACE -> 2;
            case SPRINT_SHOOTOUT -> 1;
            case QUALIFYING -> 3;
        };
    }
}
