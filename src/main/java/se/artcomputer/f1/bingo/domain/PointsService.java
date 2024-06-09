package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.controller.ResultFanDto;
import se.artcomputer.f1.bingo.controller.ResultsDto;
import se.artcomputer.f1.bingo.entity.RaceWeekend;
import se.artcomputer.f1.bingo.entity.VerifiedSession;
import se.artcomputer.f1.bingo.entity.WeekendPalette;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class PointsService {

    private final VerifyService verifyService;
    private final BingoCardService bingoCardService;
    private final WeekendPaletteService weekendPaletteService;

    public PointsService(VerifyService verifyService, BingoCardService bingoCardService, WeekendPaletteService weekendPaletteService) {
        this.verifyService = verifyService;
        this.bingoCardService = bingoCardService;
        this.weekendPaletteService = weekendPaletteService;
    }

    public StandingsDto getStandings() {
        Map<String, Long> fanPoints = new HashMap<>();
        List<VerifiedSession> verifiedSessions = verifyService.getVerifiedSessions();
        for (VerifiedSession verifiedSession : verifiedSessions) {
            RaceWeekend raceWeekend = verifiedSession.getRaceWeekend();
            Session session = verifiedSession.getSession();
            List<WeekendPalette> weekendPalettes = weekendPaletteService.getWeekendPalettes(raceWeekend);
            for (WeekendPalette weekendPalette : weekendPalettes) {
                long points = countBingos(weekendPalette, session);
                String fanName = weekendPalette.getFan().getName();
                fanPoints.put(fanName, fanPoints.getOrDefault(fanName, 0L) + points);
            }
        }
        return new StandingsDto(fanPoints.entrySet().stream()
                .map(entry -> new StandingsDto.FanScore(1, entry.getKey(), entry.getValue()))
                .toList());
    }

    private static class FanPointsPerRace {
        private final Map<RaceName, Long> points = new HashMap<>();

        public void addPoints(RaceName raceName, long sessionPoints) {
            points.put(raceName, points.getOrDefault(raceName, 0L) + sessionPoints);
        }

        public List<Long> getPoints(List<RaceName> verifiedRaces) {
            return verifiedRaces.stream().map(points::get).toList();
        }

        public long totalPoints() {
            return points.values().stream().mapToLong(Long::longValue).sum();
        }
    }

    public ResultsDto getResults() {
        List<VerifiedSession> verifiedSessions = verifyService.getVerifiedSessions();
        List<RaceName> verifiedRaces = getVerifiedRaceNames(verifiedSessions);
        Map<FanName, FanPointsPerRace> fanPoints = getFanPoints(verifiedSessions);
        List<ResultFanDto> resultFanDtos = getResultFanDtos(fanPoints, verifiedRaces);
        return new ResultsDto(verifiedRaces, resultFanDtos);
    }

    private Map<FanName, FanPointsPerRace> getFanPoints(List<VerifiedSession> verifiedSessions) {
        Map<FanName, FanPointsPerRace> fanPoints = new HashMap<>();
        for (VerifiedSession verifiedSession : verifiedSessions) {
            RaceWeekend raceWeekend = verifiedSession.getRaceWeekend();
            RaceName raceName = raceWeekend.getRaceName();
            Session session = verifiedSession.getSession();
            List<WeekendPalette> weekendPalettes = weekendPaletteService.getWeekendPalettes(raceWeekend);
            for (WeekendPalette weekendPalette : weekendPalettes) {
                long sessionPoints = countBingos(weekendPalette, session);
                FanName fanName = weekendPalette.getFan().getFanName();
                fanPoints.putIfAbsent(fanName, new FanPointsPerRace());
                fanPoints.get(fanName).addPoints(raceName, sessionPoints);
            }
        }
        return fanPoints;
    }

    private static List<ResultFanDto> getResultFanDtos(Map<FanName, FanPointsPerRace> fanPoints, List<RaceName> verifiedRaces) {
        Stream<Map.Entry<FanName, FanPointsPerRace>> fansPointEntries = fanPoints.entrySet().stream();
        return fansPointEntries
                .map(entry -> new ResultFanDto(entry.getKey(),
                        entry.getValue().getPoints(verifiedRaces),
                        entry.getValue().totalPoints()))
                .toList();
    }

    private static List<RaceName> getVerifiedRaceNames(List<VerifiedSession> verifiedSessions) {
        List<RaceName> verifiedRaces = new ArrayList<>();
        for (VerifiedSession verifiedSession : verifiedSessions) {
            RaceName raceName = verifiedSession.getRaceWeekend().getRaceName();
            if (!verifiedRaces.contains(raceName)) {
                verifiedRaces.add(raceName);
            }
        }
        return verifiedRaces;
    }

    private long countBingos(WeekendPalette weekendPalette, Session session) {
        return weekendPalette.getBingoCards().stream()
                .filter(bingoCard -> bingoCard.getSession() == session)
                .map(bingoCardService::getPoints)
                .mapToLong(Long::longValue)
                .sum();
    }

}
