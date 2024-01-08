package se.artcomputer.f1.bingo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.artcomputer.f1.bingo.domain.FanService;
import se.artcomputer.f1.bingo.entity.Fan;

import java.util.List;

@RestController
@RequestMapping("fan")
public class FanController {

    private final FanService fanService;

    public FanController(FanService fanService) {
        this.fanService = fanService;
    }

    // Get list of fans.
     @GetMapping
     public List<FanDto> fans() {
         List<Fan> fans = fanService.getFans();
         return fans.stream().map(this::toDto).toList();
     }

     private FanDto toDto(Fan fan) {
         return new FanDto(fan.getId(), fan.getName());
     }

}
