package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.entity.RaceWeekend;
import se.artcomputer.f1.bingo.entity.SessionSchedule;
import se.artcomputer.f1.bingo.repository.SessionScheduleRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
        String sessionName = getSessionName(sessionSchedule);
        return Optional.of(new GpSessionEvent(
                sessionSchedule.getId(),
                raceName,
                sessionName,
                sessionSchedule.getSummary(),
                sessionSchedule.getStartTime(),
                sessionSchedule.getEndTime()
        ));
    }

    private static final Map<String, String> SESSION_NAME_MAP = Map.of(
            "Sprint Qualification", "Sprintkval",
            "Sprint Race", "Sprint",
            "Practice 1", "FP 1",
            "Practice 2", "FP 2",
            "Practice 3", "FP 3",
            "Qualifying", "Kval",
            "Race", "Race"
    );

    private static String getSessionName(SessionSchedule sessionSchedule) {
        return SESSION_NAME_MAP.entrySet().stream()
                .filter(e -> sessionSchedule.getSummary().contains(e.getKey()))
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElse("");
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
