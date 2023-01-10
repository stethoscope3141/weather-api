package com.stethoscope.weather.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopLocationCodeDto {
    private String code;
    private String value;
    private String x;
    private String y;

    public static TopLocationCodeDto of(String code, String value, String x, String y) {
        TopLocationCodeDto dto = new TopLocationCodeDto();
        dto.code = code;
        dto.value = value;
        dto.x = x;
        dto.y = y;

        return dto;
    }
}
