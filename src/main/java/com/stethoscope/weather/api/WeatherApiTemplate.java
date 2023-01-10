package com.stethoscope.weather.api;

import com.stethoscope.weather.dto.weather.WeatherApiDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@PropertySources({
        @PropertySource("classpath:url.properties"),
        @PropertySource("classpath:api.properties"),
})
public class WeatherApiTemplate {

    @Value("${url.weather}")
    private String weatherUrl;

    @Value("${api.key}")
    private String apiKey;

    private final WebClient webClient;

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> accepts = List.of(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN);
        return headers;
    }

    public WeatherApiDto getVilageFcst(Integer x, Integer y, String baseDate) {
        HttpHeaders headers = getHeader();

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(weatherUrl)
                .path("/1360000/VilageFcstInfoService_2.0/getVilageFcst")
                .queryParam("serviceKey", apiKey)
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", "700")
                .queryParam("dataType", "JSON")
                .queryParam("base_date", baseDate)
                .queryParam("base_time", "0500")
                .queryParam("nx", x)
                .queryParam("ny", y)
                .build();

        return webClient.get()
                .uri(uriComponents.toUri())
                .headers(h -> h.addAll(headers))
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> Mono.error(new RuntimeException("weather api failed")))
                .bodyToMono(WeatherApiDto.class)
                .block();
    }
}
