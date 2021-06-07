package amogilevskiy.microservices.gateway.service;

import amogilevskiy.microservices.gateway.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final WebClient.Builder webClientBuilder;
    private final JwtService jwtService;

    @Override
    public Mono<UserProfileResponseDto> register(RegisterRequestDto dto) {
        return webClientBuilder.build()
                .post()
                .uri("lb://auth-service/1.0/auth/register")
                .body(Mono.just(new AuthRequestDto(dto)), AuthRequestDto.class)
                .retrieve()
                .onStatus(HttpStatus::isError,
                        ClientResponse::createException)
                .bodyToMono(AuthResponseDto.class)
                .flatMap(auth -> {
                    var id = jwtService.parseToken(auth.getAccessToken()).getId();
                    return webClientBuilder.build()
                            .post()
                            .uri("lb://profile-service/1.0/profiles/{id}", id)
                            .header("X-AUTH-USER-ID", String.valueOf(id))
                            .body(Mono.just(new CreateProfileRequestDto(dto)), CreateProfileRequestDto.class)
                            .retrieve()
                            .bodyToMono(ProfileResponseDto.class)
                            .map(profile -> new UserProfileResponseDto(auth, profile));
                });
    }

    @Override
    public Mono<UserProfileResponseDto> login(AuthRequestDto dto) {
        return webClientBuilder.build()
                .post()
                .uri("lb://auth-service/1.0/auth/login")
                .body(Mono.just(dto), AuthRequestDto.class)
                .retrieve()
                .onStatus(HttpStatus::isError,
                        ClientResponse::createException)
                .bodyToMono(AuthResponseDto.class)
                .flatMap(auth -> {
                    var id = jwtService.parseToken(auth.getAccessToken()).getId();
                    return webClientBuilder.build()
                            .get()
                            .uri("lb://profile-service/1.0/profiles/{id}", id)
                            .header("X-AUTH-USER-ID", String.valueOf(id))
                            .retrieve()
                            .bodyToMono(ProfileResponseDto.class)
                            .map(profile -> new UserProfileResponseDto(auth, profile));
                });
    }


}
