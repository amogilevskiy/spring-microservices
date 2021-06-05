package amogilevskiy.microservices.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(ExchangeStrategies exchangeStrategies) {
        return WebClient.builder()
                .exchangeStrategies(exchangeStrategies);
    }

    @Bean
    public ExchangeStrategies exchangeStrategies(ObjectMapper objectMapper) {
        Jackson2JsonEncoder encoder = new Jackson2JsonEncoder(objectMapper);
        Jackson2JsonDecoder decoder = new Jackson2JsonDecoder(objectMapper);
        return ExchangeStrategies
                .builder()
                .codecs(configurer -> {
                    configurer.defaultCodecs().jackson2JsonEncoder(encoder);
                    configurer.defaultCodecs().jackson2JsonDecoder(decoder);
                }).build();
    }

}
