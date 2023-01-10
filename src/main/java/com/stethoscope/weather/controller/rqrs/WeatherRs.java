package com.stethoscope.weather.controller.rqrs;

import com.stethoscope.weather.dto.weather.HourlyWeatherDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherRs {
    private List<HourlyWeatherDto> hourlyWeathers;

    public static WeatherRs of(List<HourlyWeatherDto> hourlyWeathers) {
        WeatherRs rs = new WeatherRs();
        rs.hourlyWeathers = hourlyWeathers;

        return rs;
    }
}
