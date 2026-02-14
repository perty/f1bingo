package se.artcomputer.f1.bingo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.artcomputer.f1.bingo.domain.DriverService;

import java.util.List;

@RestController
@RequestMapping("/public/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public List<DriverDto> getDrivers() {
        return driverService.findAll();
    }

    @GetMapping("/{code}")
    public DriverDto getDriver(@PathVariable String code) {
        return driverService.findByCode(code);
    }
}
