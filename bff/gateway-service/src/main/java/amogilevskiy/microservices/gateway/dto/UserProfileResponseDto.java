package amogilevskiy.microservices.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponseDto {

    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private AuthResponseDto tokens;

    public UserProfileResponseDto(AuthResponseDto auth, ProfileResponseDto profile) {
        this.id = profile.getId();
        this.username = profile.getUsername();
        this.firstName = profile.getFirstName();
        this.lastName = profile.getLastName();
        this.tokens = auth;
    }

}
