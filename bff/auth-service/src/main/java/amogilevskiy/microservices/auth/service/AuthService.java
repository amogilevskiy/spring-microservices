package amogilevskiy.microservices.auth.service;

import amogilevskiy.microservices.auth.dto.AuthRequestDto;
import amogilevskiy.microservices.auth.dto.AuthResponseDto;
import amogilevskiy.microservices.auth.dto.RegisterRequestDto;

public interface AuthService {

    AuthResponseDto login(AuthRequestDto dto);

    AuthResponseDto register(RegisterRequestDto dto);

}
