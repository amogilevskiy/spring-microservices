package amogilevskiy.microservices.profile.dto;

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

}