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

    public ResultsDto getResults() {
        List<VerifiedSession> verifiedSessions = verifyService.getVerifiedSessions();
        List<RaceName> verifiedRaces = getVerifiedRaceNames(verifiedSessions);
        Map<FanName, Map<RaceName, Long>> fanPoints = getFanPoints(verifiedSessions, verifiedRaces);
        List<ResultFanDto> list = fanPoints.entrySet().stream()
                .map(entry -> new ResultFanDto(entry.getKey(),
                        verifiedRaces.stream()
                                .map(raceName -> entry.getValue().get(raceName))
                                .toList()))
                .toList();

        return new ResultsDto(verifiedRaces, list);
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

    private Map<FanName, Map<RaceName, Long>> getFanPoints(List<VerifiedSession> verifiedSessions, List<RaceName> verifiedRaces) {
        Map<FanName, Map<RaceName, Long>> fanPoints = new HashMap<>();
        for (VerifiedSession verifiedSession : verifiedSessions) {
            RaceWeekend raceWeekend = verifiedSession.getRaceWeekend();
            RaceName raceName = raceWeekend.getRaceName();
            Session session = verifiedSession.getSession();
            List<WeekendPalette> weekendPalettes = weekendPaletteService.getWeekendPalettes(raceWeekend);
            for (WeekendPalette weekendPalette : weekendPalettes) {
                long sessionPoints = countBingos(weekendPalette, session);
                FanName fanName = weekendPalette.getFan().getFanName();
                fanPoints.putIfAbsent(fanName, initialMap(verifiedRaces));
                Map<RaceName, Long> weekendPoints = fanPoints.get(fanName);
                weekendPoints.put(raceName, weekendPoints.getOrDefault(raceName, 0L) + sessionPoints);
            }
        }
        return fanPoints;
    }

    private long countBingos(WeekendPalette weekendPalette, Session session) {
        return weekendPalette.getBingoCards().stream()
                .filter(bingoCard -> bingoCard.getSession() == session)
                .map(bingoCardService::getPoints)
                .mapToLong(Long::longValue)
                .sum();
    }

    private static Map<RaceName, Long> initialMap(List<RaceName> raceNames) {
        Map<RaceName, Long> result = new HashMap<>();
        for (RaceName raceName : raceNames) {
            result.put(raceName, 0L);
        }
        return result;
    }
}
