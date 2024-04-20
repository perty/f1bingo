package se.artcomputer.f1.bingo.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class IcalReader {

    private static String lastLocation;
    private static String dtStart;
    private static String dtEnd;
    private static String summary;
    private static long lineCounter;

    public static void main(String[] args) throws IOException {
        readICalFile(args[0]);
    }

    private static void readICalFile(String file) throws IOException {
        Stream<String> lines = Files.lines(Path.of(file));
        lines.forEach(IcalReader::processLine);

    }

    private static void processLine(String line) {
        lineCounter++;
        String[] split = line.split(":");
        String argument = split.length > 1 ? split[1].trim() : "";
        switch (split[0]) {
            case "SUMMARY":
                summary = argument;
                break;
            case "LOCATION":
                lastLocation = argument;
                break;
            case "DTSTART":
                dtStart = argument;
            case "DTEND":
                dtEnd = argument;
            case "END":
                if ("VEVENT".equals(argument)) {
                    System.out.printf("insert into session_schedule (summary, location, starttime, endtime) values ('%s', '%s', '%s', '%s');%n", summary, lastLocation, dtStart, dtEnd);
                }
                break;
            default:
                // Ignore
        }
    }
}
