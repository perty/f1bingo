package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.entity.RaceWeekend;
import se.artcomputer.f1.bingo.entity.SessionSchedule;
import se.artcomputer.f1.bingo.repository.SessionScheduleRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
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

    public Optional<Instant> findSessionStart(Long weekendId, Session session) {
        RaceWeekend raceWeekend = raceService.getRaceWeekend(weekendId);
        String country = raceWeekend.getCountry();
        Instant endDate = (raceWeekend.getEndDate().toInstant().plus(1, ChronoUnit.DAYS));
        List<SessionSchedule> byLocation = sessionScheduleRepository.findByLocationOrderByStartTime(country)
                .stream()
                .filter(sessionSchedule -> sessionSchedule.getStartTime().isBefore(endDate) &&
                        sessionSchedule.getEndTime().isAfter(raceWeekend.getStartDate().toInstant()))
                .toList();
        int index = sessionIndex(session);
        if (byLocation.size() > index) {
            return Optional.of(byLocation.get(index).getStartTime());
        }
        return Optional.empty();
    }

    public List<GpSessionEvent> findAll() {
        return sessionScheduleRepository.findAll().stream()
                .sorted(Comparator.comparing(SessionSchedule::getStartTime))
                .map(this::toEvent)
                .flatMap(Optional::stream)
                .toList();
    }

    private Optional<GpSessionEvent> toEvent(SessionSchedule sessionSchedule) {
        Date start = Date.from(sessionSchedule.getStartTime().minus(4, ChronoUnit.DAYS));
        Date end = Date.from(sessionSchedule.getEndTime().plus(2, ChronoUnit.DAYS));

        Optional<RaceWeekend> raceWeekend = raceService.findByCountry(sessionSchedule.getLocation())
                .filter(r -> r.getStartDate().after(start) && r.getEndDate().before(end))
                .findFirst();
        if (raceWeekend.isEmpty()) {
            return Optional.empty();
        }
        String raceName = raceWeekend.map(RaceWeekend::getRaceName).map(RaceName::name).orElse("");
        String sessionName = sessionSchedule.getSummary()
                .substring(sessionSchedule.getSummary().indexOf("- ") + 1)
                .replace("Practice", "FP");
        return Optional.of(new GpSessionEvent(
                sessionSchedule.getId(),
                raceName,
                sessionName,
                sessionSchedule.getSummary(),
                sessionSchedule.getStartTime(),
                sessionSchedule.getEndTime()
        ));
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
