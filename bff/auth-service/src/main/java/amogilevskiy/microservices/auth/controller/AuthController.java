package amogilevskiy.microservices.auth.controller;

import amogilevskiy.microservices.auth.dto.AuthRequestDto;
import amogilevskiy.microservices.auth.dto.AuthResponseDto;
import amogilevskiy.microservices.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/1.0/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto dto) {
        return authService.login(dto);
    }

    @PostMapping("/register")
    public AuthResponseDto register(@RequestBody AuthRequestDto dto) {
        return authService.register(dto);
    }

}
