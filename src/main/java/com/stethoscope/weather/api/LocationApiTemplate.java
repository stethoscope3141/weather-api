package com.stethoscope.weather.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("classpath:url.properties")
public class LocationApiTemplate {

    @Value("${url.location}")
    private String locationUrl;

    private final WebClient webClient;

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> accepts = List.of(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN);
        headers.setAccept(accepts);
        return headers;
    }

    public String getTopLocationCodes() {
        HttpHeaders headers = getHeader();

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(locationUrl)
                .path("/DFSROOT/POINT/DATA/top.json.txt")
                .build();

        return webClient.get()
                .uri(uriComponents.toUri())
                .headers(h -> h.addAll(headers))
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> Mono.error(new RuntimeException("location api failed")))
                .bodyToMono(String.class)
                .block();
    }

    public String getMidLocationCodes(String topCode) {
        HttpHeaders headers = getHeader();

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(locationUrl)
                .path("/DFSROOT/POINT/DATA/mdl." + topCode + ".json.txt")
                .build();

        return webClient.get()
                .uri(uriComponents.toUri())
                .headers(h -> h.addAll(headers))
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> Mono.error(new RuntimeException("location api failed")))
                .bodyToMono(String.class)
                .block();
    }

    public String getLeafLocationCodes(String midCode) {
        HttpHeaders headers = getHeader();

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(locationUrl)
                .path("/DFSROOT/POINT/DATA/leaf." + midCode + ".json.txt")
                .build();

        return webClient.get()
                .uri(uriComponents.toUri())
                .headers(h -> h.addAll(headers))
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> Mono.error(new RuntimeException("location api failed")))
                .bodyToMono(String.class)
                .block();
    }
}
