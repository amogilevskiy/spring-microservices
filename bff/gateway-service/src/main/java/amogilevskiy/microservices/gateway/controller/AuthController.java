package amogilevskiy.microservices.gateway.controller;

import amogilevskiy.microservices.gateway.dto.AuthRequestDto;
import amogilevskiy.microservices.gateway.dto.UserProfileResponseDto;
import amogilevskiy.microservices.gateway.dto.RegisterRequestDto;
import amogilevskiy.microservices.gateway.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequestMapping("/api/1.0/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public Mono<UserProfileResponseDto> register(@RequestBody RegisterRequestDto dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public Mono<UserProfileResponseDto> login(@RequestBody AuthRequestDto dto) {
        return authService.login(dto);
    }

}
