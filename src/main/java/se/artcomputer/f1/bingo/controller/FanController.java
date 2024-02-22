package se.artcomputer.f1.bingo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import se.artcomputer.f1.bingo.controller.util.GetCookie;
import se.artcomputer.f1.bingo.domain.AdminService;
import se.artcomputer.f1.bingo.domain.FanService;
import se.artcomputer.f1.bingo.entity.Fan;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("fan")
public class FanController {

    private final FanService fanService;
    private final AdminService adminService;

    public FanController(FanService fanService, AdminService adminService) {
        this.fanService = fanService;
        this.adminService = adminService;
    }

    @GetMapping
    public List<FanDto> fans() {
        List<Fan> fans = fanService.getFans();
        return fans.stream().map(this::toDto).sorted(Comparator.comparing(FanDto::name)).toList();
    }

    @PostMapping(path = "add", consumes = {"application/x-www-form-urlencoded"})
    public ResponseEntity<String> addFan(@RequestHeader HttpHeaders headers, @RequestParam MultiValueMap<String, String> map) throws URISyntaxException {
        String name = fanName(map);
        adminService.checkLogin(GetCookie.getCookie(headers), "/fan.html?addFan=" + name);
        fanService.addFan(name);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, String.valueOf(new URI("/fan.html")))
                .build();
    }

    private String fanName(MultiValueMap<String, String> map) {
        return map.getFirst("fanName");
    }

    private FanDto toDto(Fan fan) {
        return new FanDto(fan.getId(), fan.getName());
    }

}
