package com.stethoscope.weather.dto.weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherBodyDto {
    private String dataType;
    private WeatherItemListDto items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
