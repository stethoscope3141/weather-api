package com.stethoscope.weather.service;

import com.stethoscope.weather.api.LocationApiTemplate;
import com.stethoscope.weather.dto.TopLocationCodeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {
    private final LocationApiTemplate locationApiTemplate;

    public List<TopLocationCodeDto> getTopLocationCodes(String locationName) {
        String result = locationApiTemplate.getTopLocationCodes();
        return parseCodeList(result, locationName);
    }

    public List<TopLocationCodeDto> getMidLocationCodes(String topCode, String locationName) {
        String result = locationApiTemplate.getMidLocationCodes(topCode);
        return parseCodeList(result, locationName);
    }

    public List<TopLocationCodeDto> getLeafLocationCodes(String midCode, String locationName) {
        String result = locationApiTemplate.getLeafLocationCodes(midCode);
        return parseCodeList(result, locationName);
    }

    private List<TopLocationCodeDto> parseCodeList(String response, String locationName) {
        List<TopLocationCodeDto> codes = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(response);

            for (Object o : jsonArray) {
                JSONObject locationObject = (JSONObject) o;
                String code = (String) locationObject.get("code");
                String value = (String) locationObject.get("value");
                String x = (String) locationObject.get("x");
                String y = (String) locationObject.get("y");

                if (locationName == null || value.contains(locationName)) {
                    TopLocationCodeDto codeDto = new TopLocationCodeDto();
                    codeDto.setCode(code);
                    codeDto.setValue(value);
                    codeDto.setX(x);
                    codeDto.setY(y);
                    codes.add(codeDto);
                }
            }
            return codes;
        } catch (ParseException e) {
            log.error(e.getMessage());
            throw new RuntimeException("parse exception");
        }
    }
}
