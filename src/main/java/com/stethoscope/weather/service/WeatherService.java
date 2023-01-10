package com.stethoscope.weather.service;

import com.stethoscope.weather.api.WeatherApiTemplate;
import com.stethoscope.weather.dto.weather.HourlyWeatherDto;
import com.stethoscope.weather.dto.weather.WeatherApiDto;
import com.stethoscope.weather.dto.weather.WeatherItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {
    private final WeatherApiTemplate weatherApiTemplate;

    public List<HourlyWeatherDto> getTomorrow(Integer x, Integer y) {
        List<HourlyWeatherDto> hourlyWeathers = new ArrayList<>();
        String baseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        WeatherApiDto response = weatherApiTemplate.getVilageFcst(x, y, baseDate);
        List<WeatherItemDto> items = response.getResponse().getBody().getItems().getItem();

        Map<String, List<WeatherItemDto>> weatherByDateMap = items.stream()
                .collect(Collectors.groupingBy(WeatherItemDto::getFcstDate));
        String tomorrowDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        List<WeatherItemDto> tomorrowWeathers = weatherByDateMap.get(tomorrowDate);

        Map<String, List<WeatherItemDto>> weatherByTimeMap = tomorrowWeathers.stream()
                .collect(Collectors.groupingBy(WeatherItemDto::getFcstTime));

        for (Map.Entry<String, List<WeatherItemDto>> entry : weatherByTimeMap.entrySet()) {
            String time = entry.getKey();
            List<WeatherItemDto> timeWeathers = entry.getValue();
            Map<String, WeatherItemDto> weatherByCategory = timeWeathers.stream()
                    .collect(Collectors.toMap(WeatherItemDto::getCategory, Function.identity(), (o, o2) -> o));

            HourlyWeatherDto hourlyWeather = new HourlyWeatherDto();
            hourlyWeather.setTime(time);
            hourlyWeather.setRainRate(parseInt(getCategoryValue(weatherByCategory, "POP")));
            hourlyWeather.setRainType(getRainType(getCategoryValue(weatherByCategory, "PTY")));
            hourlyWeather.setHourlyRainAmount(parseInt(getCategoryValue(weatherByCategory, "PCP")));
            hourlyWeather.setHumidity(parseInt(getCategoryValue(weatherByCategory, "REH")));
            hourlyWeather.setSnowAmount(parseInt(getCategoryValue(weatherByCategory, "SNO")));
            hourlyWeather.setSky(getSkyType(getCategoryValue(weatherByCategory, "SKY")));
            hourlyWeather.setTemperature(parseInt(getCategoryValue(weatherByCategory, "TMP")));
            hourlyWeather.setWindSpeed(parseInt(getCategoryValue(weatherByCategory, "WSD")));
            hourlyWeathers.add(hourlyWeather);
        }

        return hourlyWeathers.stream().sorted(Comparator.comparing(HourlyWeatherDto::getTime)).collect(Collectors.toList());
    }

    private Integer parseInt(String pcp) {
        try {
            return Integer.parseInt(pcp);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String getSkyType(String sky) {
        switch (sky) {
            case "1":
                return "맑음";
            case "3":
                return "구름 많음";
            case "4":
                return "흐림";
            default:
                return "-";
        }
    }

    private String getRainType(String pty) {
        switch (pty) {
            case "1":
                return "비";
            case "2":
                return "비/눈";
            case "3":
                return "눈";
            case "4":
                return "소나기";
            case "0":
            default:
                return "-";
        }
    }

    private String getCategoryValue(Map<String, WeatherItemDto> weatherByCategory, String category) {
        WeatherItemDto weatherItemDto = weatherByCategory.get(category);
        if(weatherItemDto == null) {
            return "";
        }
        return weatherItemDto.getFcstValue();
    }
}
