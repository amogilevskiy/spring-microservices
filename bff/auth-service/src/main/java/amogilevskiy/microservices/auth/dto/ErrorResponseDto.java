package amogilevskiy.microservices.auth.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponseDto {

    private final String code;

}