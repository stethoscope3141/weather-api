package com.stethoscope.weather.controller.rqrs;

import com.stethoscope.weather.dto.TopLocationCodeDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocationCodeListRs {
    private List<TopLocationCodeDto> codes;

    public static LocationCodeListRs of(List<TopLocationCodeDto> codes) {
        LocationCodeListRs rs = new LocationCodeListRs();
        rs.codes = codes;

        return rs;
    }
}
