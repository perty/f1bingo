package se.artcomputer.f1.bingo.controller.publicapi;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.artcomputer.f1.bingo.domain.GpSessionEvent;
import se.artcomputer.f1.bingo.domain.RaceService;
import se.artcomputer.f1.bingo.domain.RaceWeekendType;
import se.artcomputer.f1.bingo.domain.SessionScheduleService;
import se.artcomputer.f1.bingo.entity.RaceWeekend;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("public/calendar")
public class CalendarController {
    private final RaceService raceService;
    private final SessionScheduleService sessionScheduleService;

    private static final List<String> reminders = List.of(
            "Race",
            "Qualifying",
            "Sprint Qualification",
            "Sprint Race");

    public CalendarController(RaceService raceService, SessionScheduleService sessionScheduleService) {
        this.raceService = raceService;
        this.sessionScheduleService = sessionScheduleService;
    }

    @GetMapping
    public List<CalendarDto> races() {
        List<RaceWeekend> races = raceService.getRaceWeekends();
        return races.stream().map(this::toDto).toList();
    }

    @GetMapping("/{year}")
    public List<CalendarDto> races(@PathVariable int year) {
        List<RaceWeekend> races = raceService.getRaceWeekends(year);
        return races.stream().map(this::toDto).toList();
    }

    @GetMapping(value = "/calendar.ics", produces = "text/calendar")
    public ResponseEntity<String> getCalendar() {
        List<GpSessionEvent> events = sessionScheduleService.findAll();

        StringBuilder ics = new StringBuilder();
        ics.append("BEGIN:VCALENDAR\n")
                .append("VERSION:2.0\n")
                .append("PRODID:-//agical.se//F1 Bingo//EN\n");

        for (GpSessionEvent event : events) {
            String title = event.raceName() + event.sessionName();
            ics.append("BEGIN:VEVENT\n")
                    .append("UID:").append(event.id()).append("@f1bingo.agical.se\n")
                    .append("DTSTAMP:").append(toIcsFormat(Instant.now())).append("\n")
                    .append("DTSTART:").append(toIcsFormat(event.startTime())).append("\n")
                    .append("DTEND:").append(toIcsFormat(event.endTime())).append("\n")
                    .append("SUMMARY:").append(title).append("\n")
                    .append("DESCRIPTION:").append(event.description()).append("\n");
            if (reminders.contains(event.sessionName().trim())) {
                ics.append("BEGIN:VALARM").append("\n")
                        .append("TRIGGER:-PT15M\n")
                        .append("ACTION:DISPLAY\n")
                        .append("DESCRIPTION:").append(title).append("\n")
                        .append("END:VALARM\n");
            }
            ics.append("END:VEVENT\n");
        }

        ics.append("END:VCALENDAR");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"calendar.ics\"")
                .body(ics.toString());
    }

    private CalendarDto toDto(RaceWeekend raceWeekend) {
        return new CalendarDto(
                raceWeekend.nameWithDates(),
                translateType(raceWeekend.getType()),
                raceWeekend.getCountry());
    }

    // Translate a type to a string
    private String translateType(RaceWeekendType type) {
        return switch (type) {
            case CLASSIC -> "klassisk";
            case SPRINT -> "sprint";
            case TBD -> "TBD";
        };
    }

    private String toIcsFormat(Instant dateTime) {
        return DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
                .withZone(ZoneOffset.UTC)
                .format(dateTime);
    }
}
