package com.stethoscope.weather.dto.weather;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherItemListDto {
    private List<WeatherItemDto> item;
}
