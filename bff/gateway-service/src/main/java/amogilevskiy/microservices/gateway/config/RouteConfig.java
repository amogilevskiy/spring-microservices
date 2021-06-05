package amogilevskiy.microservices.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RouteConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("profile-service", r -> r.path("/api/1.0/profiles/**")
                        .filters(filter -> filter
                                .filter(jwtAuthFilter)
                                .rewritePath("/api/(?<segment>.*)", "/${segment}"))
                        .uri("lb://profile-service"))
                .build();
    }

}
