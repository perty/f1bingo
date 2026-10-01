package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.controller.publicapi.CalendarDto;
import se.artcomputer.f1.bingo.entity.RaceWeekend;
import se.artcomputer.f1.bingo.entity.SessionSchedule;
import se.artcomputer.f1.bingo.repository.SessionScheduleRepository;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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
        Instant startDate = raceWeekend.getStartDate().toInstant();
        Instant endDate = (raceWeekend.getEndDate().toInstant().plus(1, ChronoUnit.DAYS));
        List<SessionSchedule> sessionSchedules = sessionScheduleRepository.findByStartTimeGreaterThan(startDate)
                .stream()
                .filter(sessionSchedule -> sessionSchedule.getStartTime().isBefore(endDate))
                .toList();
        int index = sessionIndex(session);
        if (sessionSchedules.size() > index) {
            return Optional.of(sessionSchedules.get(index).getStartTime());
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

    public record SessionScheduleEvent(SessionSchedule sessionSchedule) {
        public String eventName() {
            String summary = sessionSchedule.getSummary();
            String result;
            if (summary.contains("CALLED OFF")) {
                result = summary.substring(24, summary.indexOf(" - "));
            } else {
                result = summary.substring(12, summary.indexOf(" - "));
            }
            return result
                    .replace("GRAND PRIX", "GP")
                    .replace("GRANDE PRÊMIO", "GP")
                    .replace("GRAN PREMIO", "GP");
        }

        public String eventSession() {
            String summary = sessionSchedule.getSummary();
            return summary.substring(summary.indexOf("-"));
        }

        public Instant eventStartTime() {
            return sessionSchedule.getStartTime();
        }

        public Instant eventEndTime() {
            return sessionSchedule.getStartTime();
        }

        public String location() {
            return sessionSchedule.getLocation();
        }
    }

    public List<CalendarDto> toCalendar(final int year) {
        List<SessionScheduleEvent> sessionSchedules = sessionScheduleRepository.findByStartTimeGreaterThan(getFirstDay(year)).stream()
                .map(SessionScheduleEvent::new)
                .toList();
        Map<String, List<SessionScheduleEvent>> weekends = sessionSchedules.stream()
                .collect(Collectors.groupingBy(SessionScheduleEvent::eventName));
        return weekends.values().stream()
                .map(WeekendEvent::new)
                .sorted(Comparator.comparing(WeekendEvent::startDate))
                .map(this::toCalendarDto).toList();
    }

    private static Instant getFirstDay(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        Date firstDay = cal.getTime();
        return firstDay.toInstant();
    }

    record WeekendEvent(List<SessionScheduleEvent> sessionScheduleEvents) {
        private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
        private static final SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");

        public String eventNameWithDates() {
            return "%s %s - %s %s".formatted(
                    sessionScheduleEvents().getFirst().eventName(),
                    formatter.format(startDate()),
                    formatter.format(endDate()),
                    yearFormatter.format(startDate()));
        }

        public String location() {
            return sessionScheduleEvents().getFirst().location();
        }

        public Date startDate() {
            Instant instant = sessionScheduleEvents.stream().min(Comparator.comparing(SessionScheduleEvent::eventStartTime)).orElseThrow().eventStartTime();
            return Date.from(instant);
        }

        private Date endDate() {
            Instant instant = sessionScheduleEvents.stream().max(Comparator.comparing(SessionScheduleEvent::eventEndTime)).orElseThrow().eventEndTime();
            return Date.from(instant);
        }

        public String type() {
            if(sessionScheduleEvents.getFirst().sessionSchedule().getSummary().contains("CALLED OFF")) {
                return "CALLED OFF";
            }
            if (isAnyMatch("TESTING")) {
                return "TESTING";
            }
            if (isAnyMatch("Sprint Race")) {
                return "SPRINT";
            }
            return "CLASSIC";
        }

        private boolean isAnyMatch(String testValue) {
            return sessionScheduleEvents.stream().anyMatch(s -> s.eventSession().contains(testValue));
        }
    }

    private CalendarDto toCalendarDto(WeekendEvent weekendEvent) {
        return new CalendarDto(weekendEvent.eventNameWithDates(),weekendEvent.type(), weekendEvent.location());
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
