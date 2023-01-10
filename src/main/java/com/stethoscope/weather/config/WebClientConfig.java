package com.stethoscope.weather.config;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        HttpClient client = createHttpClient();
        // 16MB로 DevOps 김재훈님과 협의함
        ExchangeStrategies strategies = getExchangeStrategies();
        var connector = new ReactorClientHttpConnector(client);
        return WebClient.builder().exchangeStrategies(strategies).clientConnector(connector).build();
    }

    public static HttpClient createHttpClient() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(EpollChannelOption.TCP_KEEPIDLE, 300)
                .option(EpollChannelOption.TCP_KEEPINTVL, 60)
                .option(EpollChannelOption.TCP_KEEPCNT, 8)
                .responseTimeout(Duration.ofSeconds(10));
    }

    public static ExchangeStrategies getExchangeStrategies() {
        return ExchangeStrategies.builder().codecs(
                configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build();
    }
}
