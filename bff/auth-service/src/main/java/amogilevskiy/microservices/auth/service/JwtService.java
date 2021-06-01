package amogilevskiy.microservices.auth.service;

import amogilevskiy.microservices.auth.domain.User;
import amogilevskiy.microservices.auth.dto.UserTokenDto;

public interface JwtService {

    String generateToken(User user, TokenType type);

    UserTokenDto parseToken(String token);

}
