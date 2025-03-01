package se.artcomputer.f1.bingo.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class IcalReader {

    private String location;
    private String dtStart;
    private String dtEnd;
    private String summary;
    private boolean inSummary;

    public static void main(String[] args) throws IOException {
        new IcalReader().readICalFile(args[0]);
    }

    private void readICalFile(String file) throws IOException {
        Stream<String> lines = Files.lines(Path.of(file));
        lines.forEach(this::processLine);
    }

    private void processLine(String line) {
        String[] split = line.split(":");
        String argument = split.length > 1 ? line.substring(line.indexOf(':') + 1) : "";
        if (argument.isEmpty() && inSummary && line.startsWith(" ")) {
            summary += line.substring( 1);
        }
        switch (split[0]) {
            case "SUMMARY":
                summary = argument;
                inSummary = true;
                break;
            case "LOCATION":
                location = argument;
                inSummary = false;
                break;
            case "DTSTART":
                dtStart = argument;
                inSummary = false;
                break;
            case "DTEND":
                dtEnd = argument;
                inSummary = false;
                break;
            case "END":
                if ("VEVENT".equals(argument)) {
                    System.out.printf("insert into session_schedule (summary, location, starttime, endtime) values ('%s', '%s', '%s', '%s');%n", summary, location, dtStart, dtEnd);
                }
                break;
            default:
                inSummary = false;
        }
    }
}
