package amogilevskiy.microservices.auth.service;

import amogilevskiy.microservices.auth.domain.User;
import amogilevskiy.microservices.auth.dto.AuthRequestDto;
import amogilevskiy.microservices.auth.dto.AuthResponseDto;
import amogilevskiy.microservices.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService tokenService;

    @Override
    public AuthResponseDto login(AuthRequestDto dto) {
        return userRepository.findByUsername(dto.getUsername())
                .map(user -> {
                    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                        throw new InvalidCredentialsException();
                    }
                    return generateAuthResponse(user);
                })
                .orElseThrow(InvalidCredentialsException::new);
    }

    @Transactional
    @Override
    public AuthResponseDto register(AuthRequestDto dto) {
        var user = User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
        try {
            return generateAuthResponse(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException();
        }
    }

    private AuthResponseDto generateAuthResponse(User user) {
        var accessToken = tokenService.generateToken(user, TokenType.ACCESS);
        var refreshToken = tokenService.generateToken(user, TokenType.REFRESH);
        return new AuthResponseDto(accessToken, refreshToken);
    }

}
