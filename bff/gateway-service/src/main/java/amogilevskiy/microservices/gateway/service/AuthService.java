package amogilevskiy.microservices.gateway.service;

import amogilevskiy.microservices.gateway.dto.RegisterRequestDto;
import amogilevskiy.microservices.gateway.dto.UserProfileResponseDto;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<UserProfileResponseDto> register(RegisterRequestDto dto);

}
