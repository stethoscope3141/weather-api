package com.stethoscope.weather.controller;

import com.stethoscope.weather.controller.rqrs.WeatherRs;
import com.stethoscope.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
@Slf4j
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/tomorrow")
    public WeatherRs getTodayWeather(@RequestParam Integer x,
                                     @RequestParam Integer y) {
        return WeatherRs.of(weatherService.getTomorrow(x,y));
    }
}