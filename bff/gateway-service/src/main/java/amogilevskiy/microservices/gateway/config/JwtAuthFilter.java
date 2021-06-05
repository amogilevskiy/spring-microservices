package amogilevskiy.microservices.gateway.config;

import amogilevskiy.microservices.gateway.service.InvalidTokenException;
import amogilevskiy.microservices.gateway.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements GatewayFilter {

    public static final List<String> NOT_PROTECTED_ENDPOINTS = List.of(
            "/api/1.0/auth/login",
            "/api/1.0/auth/register"
    );
    private static final int BEARER_PREFIX_LENGTH = 7;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTH_HEADER_USER_ID = "X-AUTH-USER-ID";
    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var request = exchange.getRequest();

        if (isProtectedRequest(request)) {
            if (isAuthHeaderMissing(request))
                return onError(exchange, "missing_token", HttpStatus.UNAUTHORIZED);
            try {
                appendAuthHeaders(exchange, getAuthHeader(request));
            } catch (InvalidTokenException e) {
                return onError(exchange, "invalid_token", HttpStatus.UNAUTHORIZED);
            }
        }

        return chain.filter(exchange);
    }

    private boolean isProtectedRequest(ServerHttpRequest request) {
        return NOT_PROTECTED_ENDPOINTS
                .stream()
                .noneMatch(uri -> request.getURI().getPath().contains(uri));
    }

    private boolean isAuthHeaderMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(AUTHORIZATION_HEADER);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
        var response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(AUTHORIZATION_HEADER).get(0);
    }

    private void appendAuthHeaders(ServerWebExchange exchange, String authHeader) {
        var jwt = authHeader.substring(BEARER_PREFIX_LENGTH);

        var dto = jwtService.parseToken(jwt);
        exchange.getRequest().mutate()
                .header(AUTH_HEADER_USER_ID, String.valueOf(dto.getId()))
                .build();
    }

}
