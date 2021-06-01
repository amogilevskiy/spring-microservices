package amogilevskiy.microservices.profile.service;

import amogilevskiy.microservices.profile.domain.AuthUser;
import amogilevskiy.microservices.profile.domain.Profile;
import amogilevskiy.microservices.profile.dto.ProfileResponseDto;
import amogilevskiy.microservices.profile.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ProfileMapper profileMapper;

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Test
    void shouldRaiseExceptionWhenProfileNotFound() {
        var id = 1L;
        when(profileRepository.findById(id)).thenReturn(Optional.empty());

        var e = assertThrows(ProfileNotFoundException.class, () -> {
            profileService.findById(id, new AuthUser(id));
        });
        assertThat(e.getMessage()).isEqualTo("profile_not_found");
    }

    @Test
    void shouldRaiseExceptionWhenAuthorized() {
        var id = 1L;
        var authUserId = 2L;

        assertThrows(ForbiddenException.class, () -> profileService.findById(id, new AuthUser(authUserId)));
    }

    @Test
    void shouldReturnProfileById() {
        var id = 1L;
        var profile = new Profile(id, "username", "first_name", "last_name");
        var expectedResponse = new ProfileResponseDto(id, profile.getUsername(), profile.getFirstName(), profile.getLastName());
        when(profileRepository.findById(id)).thenReturn(Optional.of(profile));
        when(profileMapper.toResponseDto(profile)).thenReturn(expectedResponse);

        var actualResponse = profileService.findById(id, new AuthUser(id));

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

}
