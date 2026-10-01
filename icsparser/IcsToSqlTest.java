public final class IcsToSqlTest {
    public static void main(String[] args) {
        String event = "BEGIN:VCALENDAR\r\nBEGIN:VEVENT\r\n"
                + "SUMMARY:🏎 O'Brien\\, GP\r\n continued\r\n"
                + "DTSTART:20261001T120000Z\r\nDTEND:20261001T130000Z\r\n"
                + "LOCATION:Room\\; A\\nFloor 2\\\\West\r\n"
                + "BEGIN:VALARM\r\nSUMMARY:Ignore alarm\r\nEND:VALARM\r\n"
                + "END:VEVENT\r\nEND:VCALENDAR\r\n";
        equal("INSERT INTO races (Summary, starttime, endtime, location) VALUES "
                + "('🏎 O''Brien, GPcontinued', '2026-10-01 12:00:00', '2026-10-01 13:00:00', 'Room; A\nFloor 2\\West');\n",
                IcsToSql.convert(event));
        equal("INSERT INTO events (Summary, starttime, endtime, location) VALUES "
                + "(NULL, '2026-10-01 10:00:00', NULL, NULL);\n",
                IcsToSql.convert(calendar("DTSTART;TZID=\"Europe/Stockholm\":20261001T120000")));
        equal("INSERT INTO events (Summary, starttime, endtime, location) VALUES "
                + "(NULL, '2026-10-01 00:00:00', '2026-10-02 00:00:00', NULL);\n",
                IcsToSql.convert(calendar("DTSTART;VALUE=DATE:20261001\nDTEND;VALUE=DATE:20261002")));
        equal("INSERT INTO events (Summary, starttime, endtime, location) VALUES "
                + "(NULL, '2026-10-01 12:00:00', NULL, NULL);\n",
                IcsToSql.convert(calendar("DTSTART:20261001T120000")));
        fails(() -> IcsToSql.convert(event));
        fails(() -> IcsToSql.convert(calendar("SUMMARY:Missing start")));
        fails(() -> IcsToSql.convert(calendar("DTSTART:20260230T120000Z")));
        fails(() -> IcsToSql.convert(calendar("DTSTART;TZID=MadeUp/Zone:20261001T120000")));
        fails(() -> IcsToSql.convert(calendar("DTSTART;TZID=Europe/Stockholm:20261025T023000")));
        fails(() -> IcsToSql.convert(event.replace("END:VEVENT", "END:VTODO")));
        fails(() -> IcsToSql.convert(calendar("DTSTART:20261001T120000Z\nDURATION:PT1H")));
        equal("", IcsToSql.convert("BEGIN:VCALENDAR\nEND:VCALENDAR"));
        System.out.println("All tests passed.");
    }

    private static String calendar(String properties) {
        return "BEGIN:VCALENDAR\nBEGIN:VEVENT\n" + properties + "\nEND:VEVENT\nEND:VCALENDAR";
    }

    private static void equal(String expected, String actual) {
        if (!expected.equals(actual)) throw new AssertionError("Expected: " + expected + "Actual: " + actual);
    }

    private static void fails(Runnable action) {
        try { action.run(); } catch (RuntimeException expected) { return; }
        throw new AssertionError("Expected conversion to fail");
    }
}
