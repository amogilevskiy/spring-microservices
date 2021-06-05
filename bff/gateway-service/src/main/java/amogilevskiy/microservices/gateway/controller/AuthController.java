package amogilevskiy.microservices.gateway.controller;

import amogilevskiy.microservices.gateway.dto.UserProfileResponseDto;
import amogilevskiy.microservices.gateway.dto.RegisterRequestDto;
import amogilevskiy.microservices.gateway.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("/api/1.0/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public Mono<UserProfileResponseDto> register(@RequestBody RegisterRequestDto dto) {
        return authService.register(dto);
    }

}
