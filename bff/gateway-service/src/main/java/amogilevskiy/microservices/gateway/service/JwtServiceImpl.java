package amogilevskiy.microservices.gateway.service;

import amogilevskiy.microservices.gateway.config.JwtProps;
import amogilevskiy.microservices.gateway.dto.UserTokenDto;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

import java.text.ParseException;


@Service
public class JwtServiceImpl implements JwtService {

    private final JwtProps props;
    private JWSVerifier verifier;

    public JwtServiceImpl(JwtProps props) throws JOSEException {
        this.props = props;

        var secret = props.getSecret().getBytes();
        verifier = new MACVerifier(secret);
    }

    @Override
    public UserTokenDto parseToken(String token) {
        try {
            var jwt = SignedJWT.parse(token);
            if (jwt.verify(verifier)) {
                var claims = jwt.getJWTClaimsSet();
                return new UserTokenDto(Long.parseLong(claims.getSubject()));
            } else {
                throw new InvalidTokenException();
            }
        } catch (ParseException | JOSEException e) {
            throw new InvalidTokenException();
        }
    }

}