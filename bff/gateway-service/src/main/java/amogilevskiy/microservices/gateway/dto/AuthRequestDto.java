package amogilevskiy.microservices.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {

    private String username;
    private String password;

    public AuthRequestDto(RegisterRequestDto dto) {
        this.username = dto.getUsername();
        this.password = dto.getPassword();
    }

}
