package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.entity.RaceWeekend;
import se.artcomputer.f1.bingo.entity.VerifiedSession;
import se.artcomputer.f1.bingo.entity.WeekendPalette;

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
        for(VerifiedSession verifiedSession : verifiedSessions) {
            RaceWeekend raceWeekend = verifiedSession.getRaceWeekend();
            Session session = verifiedSession.getSession();
            List<WeekendPalette> weekendPalettes = weekendPaletteService.getWeekendPalettes(raceWeekend);
            for(WeekendPalette weekendPalette : weekendPalettes) {
                long points = weekendPalette.getBingoCards().stream()
                        .filter(bingoCard -> bingoCard.getSession() == session)
                        .map(bingoCardService::getPoints)
                        .mapToLong(Long::longValue)
                        .sum();
                String fanName = weekendPalette.getFan().getName();
                fanPoints.put(fanName, fanPoints.getOrDefault(fanName, 0L) + points);
            }
        }
        return new StandingsDto(fanPoints.entrySet().stream()
                .map(entry -> new StandingsDto.FanScore(1, entry.getKey(), entry.getValue()))
                .toList());
    }
}
