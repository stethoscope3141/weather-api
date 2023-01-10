package com.stethoscope.weather.dto.weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HourlyWeatherDto {
    private String time;

    private Integer rainRate;   // 강수확률 %
    private String rainType;    // 강수형태
    private Integer hourlyRainAmount; // 1시간 강수량 (mm)
    private Integer humidity;   // 습도 %
    private Integer snowAmount;   // 1시간 신적설 cm
    private String sky;     // 하늘상태
    private Integer temperature;    // 기온
    private Integer windSpeed;  // 풍속 m/s
}
