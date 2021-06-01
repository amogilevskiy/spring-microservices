package amogilevskiy.microservices.auth.service;

import amogilevskiy.microservices.auth.config.JwtProps;
import amogilevskiy.microservices.auth.domain.User;
import amogilevskiy.microservices.auth.dto.UserTokenDto;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private final JwtProps props;
    private JWSSigner signer;
    private JWSVerifier verifier;

    public JwtServiceImpl(JwtProps props) throws JOSEException {
        this.props = props;

        var secret = props.getSecret().getBytes();
        signer = new MACSigner(secret);
        verifier = new MACVerifier(secret);
    }

    @Override
    public String generateToken(User user, TokenType type) {
        var iat = System.currentTimeMillis();
        var expirationInMins = props.getExpiration().get(type.getCode());
        var expirationInMillis = expirationInMins * 60 * 1000;

        var claims = new JWTClaimsSet.Builder()
                .subject(user.getId().toString())
                .issueTime(new Date(iat))
                .expirationTime(new Date(iat + expirationInMillis))
                .build();
        var jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
        try {
            jwt.sign(signer);
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return jwt.serialize();
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
