package amogilevskiy.microservices.auth.service;

import amogilevskiy.microservices.auth.config.JwtProps;
import amogilevskiy.microservices.auth.domain.User;
import com.nimbusds.jose.JOSEException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtServiceTest {

    private JwtServiceImpl tokenService;

    @BeforeEach
    void beforeEach() throws JOSEException {
        var props = new JwtProps();
        props.setSecret("TEST1111222233334444555566667777");
        props.setExpiration(Map.of(
                TokenType.ACCESS.getCode(), 5L
        ));
        tokenService = new JwtServiceImpl(props);
    }

    @Test
    void shouldReturnValidToken() {
        var user = new User(1L, "user", "password");

        var stringToken = tokenService.generateToken(user, TokenType.ACCESS);
        var parsedToken = tokenService.parseToken(stringToken);

        assertThat(stringToken).isNotBlank();
        Assertions.assertThat(parsedToken.getId()).isEqualTo(user.getId());
    }

}
