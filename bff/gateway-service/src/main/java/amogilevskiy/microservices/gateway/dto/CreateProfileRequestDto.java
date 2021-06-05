package amogilevskiy.microservices.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProfileRequestDto {

    private String username;
    private String firstName;
    private String lastName;

    public CreateProfileRequestDto(RegisterRequestDto dto) {
        this.username = dto.getUsername();
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
    }

}
