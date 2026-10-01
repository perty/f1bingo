import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.*;

/**
 * A small ICS VEVENT to SQL command-line converter. Requires Java 11+.
 */
public final class IcsToSql {
    private static final DateTimeFormatter ICS_TIME = DateTimeFormatter
            .ofPattern("uuuuMMdd'T'HHmmss").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter SQL_TIME = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
    public static final String TABLE = "session_schedule";

    public static void main(String[] args) {
        if (args.length == 1 && (args[0].equals("--help") || args[0].equals("-h"))) {
            System.out.println("Usage: java IcsToSql.java <calendar.ics> ");
            return;
        }
        if (args.length < 1 || args.length > 2) {
            System.err.println("Usage: java IcsToSql.java <calendar.ics> ");
            System.exit(2);
        }
        try {
            Path input = Path.of(args[0]);
            String sql = convert(Files.readString(input, StandardCharsets.UTF_8));
            new PrintStream(System.out, true, StandardCharsets.UTF_8).print(sql);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    static String convert(String ics) {
        List<String> lines = createLines(ics);
        Deque<String> components = new ArrayDeque<>();
        Map<String, Property> event = null;
        StringBuilder sql = new StringBuilder();
        boolean calendarSeen = false;
        int eventNumber = 0;
        for (String line : lines) {
            Property property = Property.parse(line);
            if (property.name.equals("BEGIN")) {
                String component = property.value.toUpperCase(Locale.ROOT);
                if (components.isEmpty()) {
                    if (!component.equals("VCALENDAR") || calendarSeen)
                        throw new IllegalArgumentException("Expected one VCALENDAR");
                    calendarSeen = true;
                }
                if (component.equals("VEVENT")) {
                    if (!"VCALENDAR".equals(components.peek()))
                        throw new IllegalArgumentException("VEVENT must be inside VCALENDAR");
                    event = new HashMap<>();
                    eventNumber++;
                }
                components.push(component);
            } else if (property.name.equals("END")) {
                if (components.isEmpty() || !components.pop().equalsIgnoreCase(property.value))
                    throw new IllegalArgumentException("Mismatched END: " + property.value);
                if (property.value.equalsIgnoreCase("VEVENT")) {
                    try {
                        sql.append(toInsert(event));
                    } catch (RuntimeException e) {
                        throw new IllegalArgumentException("Event " + eventNumber + ": " + e.getMessage(), e);
                    }
                    event = null;
                }
            } else if (components.isEmpty()) {
                throw new IllegalArgumentException("Property outside VCALENDAR");
            } else if ("VEVENT".equals(components.peek())) {
                if (Arrays.asList("SUMMARY", "DTSTART", "DTEND", "LOCATION", "DURATION").contains(property.name)) {
                    if (event.putIfAbsent(property.name, property) != null)
                        throw new IllegalArgumentException("Duplicate " + property.name + " in event " + eventNumber);
                }
            }
        }
        if (!calendarSeen || !components.isEmpty()) throw new IllegalArgumentException("Incomplete VCALENDAR");
        return sql.toString();
    }

    private static List<String> createLines(String ics) {
        List<String> lines = new ArrayList<>();
        for (String line : ics.replaceFirst("^\uFEFF", "").split("\\r\\n|\\n|\\r", -1)) {
            if (line.startsWith(" ") || line.startsWith("\t")) {
                if (lines.isEmpty()) throw new IllegalArgumentException("Folded line without preceding line");
                int last = lines.size() - 1;
                lines.set(last, lines.get(last) + line.substring(1));
            } else if (!line.isEmpty()) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static String toInsert(Map<String, Property> event) {
        Property start = event.get("DTSTART");
        if (start == null) throw new IllegalArgumentException("Missing DTSTART");
        if (event.containsKey("DURATION"))
            throw new IllegalArgumentException("DURATION is not supported; use DTEND");
        String startTime = timestamp(start);
        Property end = event.get("DTEND");
        String endTime = end == null ? null : timestamp(end);
        return "INSERT INTO " + TABLE + " (Summary, starttime, endtime, location) VALUES ("
                + quote(text(event.get("SUMMARY"))) + ", " + quote(startTime) + ", "
                + quote(endTime) + ", " + quote(text(event.get("LOCATION"))) + ");\n";
    }

    private static String timestamp(Property p) {
        if ("DATE".equalsIgnoreCase(p.parameters.get("VALUE"))) {
            return LocalDate.parse(p.value, DateTimeFormatter.BASIC_ISO_DATE).atStartOfDay().format(SQL_TIME);
        }
        String valueType = p.parameters.getOrDefault("VALUE", "DATE-TIME");
        if (!valueType.equalsIgnoreCase("DATE-TIME"))
            throw new IllegalArgumentException("Unsupported time value type: " + valueType);
        boolean utc = p.value.endsWith("Z");
        String value = utc ? p.value.substring(0, p.value.length() - 1) : p.value;
        LocalDateTime time = LocalDateTime.parse(value, ICS_TIME);
        String zone = p.parameters.get("TZID");
        if (utc && zone != null) throw new IllegalArgumentException("UTC time cannot also have TZID");
        if (zone != null) {
            ZoneId zoneId = ZoneId.of(zone);
            // Ambiguous or nonexistent local times require an explicit UTC value.
            List<ZoneOffset> offsets = zoneId.getRules().getValidOffsets(time);
            if (offsets.size() != 1) throw new IllegalArgumentException("Ambiguous/nonexistent local time: " + p.value);
            time = time.atOffset(offsets.getFirst()).withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime();
        }
        return time.format(SQL_TIME);
    }

    private static String text(Property p) {
        if (p == null) return null;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < p.value.length(); i++) {
            char c = p.value.charAt(i);
            if (c == '\\' && i + 1 < p.value.length()) {
                char next = p.value.charAt(++i);
                if (next == 'n' || next == 'N') result.append('\n');
                else if (next == '\\' || next == ',' || next == ';') result.append(next);
                else throw new IllegalArgumentException("Unsupported text escape: \\" + next);
            } else if (c == '\\') {
                throw new IllegalArgumentException("Incomplete text escape");
            } else result.append(c);
        }
        return result.toString();
    }

    private static String quote(String value) {
        return value == null ? "NULL" : "'" + value.replace("'", "''") + "'";
    }

    private static final class Property {
        final String name;
        final Map<String, String> parameters;
        final String value;

        Property(String name, Map<String, String> parameters, String value) {
            this.name = name;
            this.parameters = parameters;
            this.value = value;
        }

        static Property parse(String line) {
            List<String> parts = new ArrayList<>();
            boolean quoted = false;
            int from = 0;
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == '"') quoted = !quoted;
                if (!quoted && (c == ';' || c == ':')) {
                    parts.add(line.substring(from, i));
                    from = i + 1;
                    if (c == ':') {
                        Map<String, String> parameters = new HashMap<>();
                        for (int j = 1; j < parts.size(); j++) {
                            String[] pair = parts.get(j).split("=", 2);
                            if (pair.length != 2)
                                throw new IllegalArgumentException("Invalid parameter: " + parts.get(j));
                            String value = pair[1];
                            if (value.startsWith("\"") && value.endsWith("\""))
                                value = value.substring(1, value.length() - 1);
                            parameters.put(pair[0].toUpperCase(Locale.ROOT), value);
                        }
                        return new Property(parts.getFirst().toUpperCase(Locale.ROOT), parameters, line.substring(from));
                    }
                }
            }
            throw new IllegalArgumentException("Invalid ICS line: " + line);
        }
    }
}
