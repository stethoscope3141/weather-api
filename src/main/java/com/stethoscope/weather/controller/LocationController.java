package com.stethoscope.weather.controller;

import com.stethoscope.weather.controller.rqrs.LocationCodeListRs;
import com.stethoscope.weather.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
@Slf4j
public class LocationController {
    private final LocationService locationService;

    @GetMapping("/top")
    public LocationCodeListRs getTopLocationCodes(@RequestParam(required = false) String name) {
        return LocationCodeListRs.of(locationService.getTopLocationCodes(name));
    }

    @GetMapping("/mid")
    public LocationCodeListRs getMidLocationCodes(@RequestParam(value = "code") String topCode,
                                                  @RequestParam(required = false) String name) {
        return LocationCodeListRs.of(locationService.getMidLocationCodes(topCode, name));
    }

    @GetMapping("/leaf")
    public LocationCodeListRs getLeafLocationCodes(@RequestParam(value = "code") String midCode,
                                                   @RequestParam(required = false) String name) {
        return LocationCodeListRs.of(locationService.getLeafLocationCodes(midCode, name));
    }
}