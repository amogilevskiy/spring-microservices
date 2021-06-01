package amogilevskiy.microservices.auth.service;

import amogilevskiy.microservices.auth.domain.User;
import amogilevskiy.microservices.auth.dto.AuthRequestDto;
import amogilevskiy.microservices.auth.dto.AuthResponseDto;
import amogilevskiy.microservices.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService tokenService;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void shouldRaiseExceptionWhenUserNotFound() {
        var username = "tester";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        var e = assertThrows(InvalidCredentialsException.class, () -> {
            authService.login(new AuthRequestDto(username, ""));
        });
        assertThat(e.getMessage()).isEqualTo("invalid_credentials");
    }

    @Test
    void shouldRaiseExceptionWhenPasswordNotValid() {
        var user = new User(1L, "username", "password");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        var e = assertThrows(InvalidCredentialsException.class, () -> {
            authService.login(new AuthRequestDto(user.getUsername(), ""));
        });
        assertThat(e.getMessage()).isEqualTo("invalid_credentials");
    }

    @Test
    void shouldLogin() {
        var user = new User(1L, "username", "password");
        var expectedToken = "token";
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(tokenService.generateToken(any(), any())).thenReturn(expectedToken);
        var expectedResponse = new AuthResponseDto(expectedToken, expectedToken);

        var actualResponse = authService.login(new AuthRequestDto(user.getUsername(), user.getPassword()));

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

}
