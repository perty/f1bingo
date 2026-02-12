package se.artcomputer.f1.bingo;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Usage:
 *   java IcsToPostgresSql.java /path/to/calendar.ics > insert.sql
 * Produces:
 *   insert into session_schedule (summary, location, starttime, endtime) values (...), (...), ...;
 */
public class IcsToPostgresSql {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java IcsToPostgresSql <path-to-ics>");
            System.exit(2);
        }

        Path icsPath = Path.of(args[0]);
        String raw = Files.readString(icsPath, StandardCharsets.UTF_8);

        List<String> lines = unfoldLines(raw);
        List<Event> events = parseEvents(lines);

        // Sort by starttime then summary (nice stable output)
        events.sort(Comparator
                .comparing((Event e) -> e.start)
                .thenComparing(e -> e.summary));

        // Emit SQL
        System.out.println("insert into session_schedule (summary, location, starttime, endtime) values");
        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            String tuple = String.format("('%s', '%s', '%s', '%s')",
                    sqlEscape(e.summary),
                    sqlEscape(e.location),
                    sqlEscape(e.start),
                    sqlEscape(e.end)
            );
            System.out.print(tuple);
            System.out.println(i == events.size() - 1 ? ";" : ",");
        }
    }

    /**
     * iCalendar line unfolding:
     * Lines that start with a single space or tab are continuations of the previous line.
     */
    static List<String> unfoldLines(String raw) {
        String[] split = raw.replace("\r\n", "\n").replace("\r", "\n").split("\n", -1);
        List<String> out = new ArrayList<>(split.length);

        StringBuilder current = new StringBuilder();
        for (String line : split) {
            if (line.isEmpty()) {
                // blank line: flush any buffered line (rare in ICS but safe)
                if (!current.isEmpty()) {
                    out.add(current.toString());
                    current.setLength(0);
                }
                continue;
            }

            char c0 = line.charAt(0);
            boolean isContinuation = (c0 == ' ' || c0 == '\t');

            if (isContinuation) {
                // append without the leading whitespace
                current.append(line.substring(1));
            } else {
                // flush previous
                if (!current.isEmpty()) out.add(current.toString());
                current.setLength(0);
                current.append(line);
            }
        }
        if (!current.isEmpty()) out.add(current.toString());
        return out;
    }

    static List<Event> parseEvents(List<String> lines) {
        boolean inEvent = false;
        Map<String, String> fields = new HashMap<>();
        Set<EventKey> seen = new HashSet<>();
        List<Event> out = new ArrayList<>();

        for (String line : lines) {
            if (line.equals("BEGIN:VEVENT")) {
                inEvent = true;
                fields.clear();
                continue;
            }
            if (line.equals("END:VEVENT")) {
                if (inEvent) {
                    Event e = eventFromFields(fields);
                    if (e != null) {
                        EventKey key = new EventKey(e.summary, e.location, e.start, e.end);
                        if (seen.add(key)) out.add(e);
                    }
                }
                inEvent = false;
                fields.clear();
                continue;
            }

            if (!inEvent) continue;

            // We only care about SUMMARY, LOCATION, DTSTART*, DTEND*
            // Lines can have params: DTSTART;VALUE=DATE:20260212
            // or plain: DTSTART:20260501T163000Z
            int colonIdx = line.indexOf(':');
            if (colonIdx <= 0) continue;

            String left = line.substring(0, colonIdx);
            String value = line.substring(colonIdx + 1);

            String propName = left;
            int semiIdx = left.indexOf(';');
            if (semiIdx > 0) propName = left.substring(0, semiIdx);

            switch (propName) {
                case "SUMMARY" -> fields.put("SUMMARY", value);
                case "LOCATION" -> fields.put("LOCATION", value);
                case "DTSTART" -> fields.put("DTSTART_RAW", line); // keep full line (for params)
                case "DTEND" -> fields.put("DTEND_RAW", line);     // keep full line (for params)
                default -> { /* ignore */ }
            }
        }

        return out;
    }

    static Event eventFromFields(Map<String, String> fields) {
        String summary = fields.getOrDefault("SUMMARY", "").trim();
        if (summary.isEmpty()) return null;

        String location = fields.getOrDefault("LOCATION", "").trim();
        if (location.isEmpty()) location = "Unknown";

        String dtStartLine = fields.get("DTSTART_RAW");
        String dtEndLine = fields.get("DTEND_RAW");
        if (dtStartLine == null || dtEndLine == null) return null;

        String start = normalizeDateTimeValue(dtStartLine);
        String end = normalizeDateTimeValue(dtEndLine);
        if (start == null || end == null) return null;

        return new Event(summary, location, start, end);
    }

    /**
     * Accepts full line like:
     *   DTSTART:20260501T163000Z
     *   DTSTART;VALUE=DATE:20260212
     * Returns a string in the style you used: YYYYMMDDTHHMMSSZ
     */
    static String normalizeDateTimeValue(String fullLine) {
        int colonIdx = fullLine.indexOf(':');
        if (colonIdx <= 0) return null;

        String left = fullLine.substring(0, colonIdx);
        String value = fullLine.substring(colonIdx + 1).trim();

        boolean isDateOnly = left.contains("VALUE=DATE");

        if (isDateOnly) {
            // YYYYMMDD -> YYYYMMDDT000000Z
            if (value.length() != 8) return null;
            return value + "T000000Z";
        }

        // Most F1 ECAL entries are already UTC like 20260501T163000Z
        // Ensure seconds exist (HHMMSS). If only HHMM -> pad :00
        // Common forms:
        //  - YYYYMMDDTHHMMSSZ (ok)
        //  - YYYYMMDDTHHMMZ   (pad seconds)
        if (value.endsWith("Z")) {
            String core = value.substring(0, value.length() - 1);
            int t = core.indexOf('T');
            if (t < 0) return null;

            String date = core.substring(0, t);
            String time = core.substring(t + 1);

            if (date.length() != 8) return null;

            if (time.length() == 6) {
                return value; // already HHMMSSZ
            } else if (time.length() == 4) {
                return date + "T" + time + "00Z";
            } else {
                return value; // leave as-is (or you can be stricter)
            }
        }

        // If not Z-terminated, still return as-is (or convert if you want)
        return value;
    }

    static String sqlEscape(String s) {
        // Postgres standard single-quote escaping
        return s.replace("'", "''");
    }

    record Event(String summary, String location, String start, String end) {}
    record EventKey(String summary, String location, String start, String end) {}
}
