package amogilevskiy.microservices.gateway.service;

import amogilevskiy.microservices.gateway.dto.UserTokenDto;

public interface JwtService {

    UserTokenDto parseToken(String token);

}
