package amogilevskiy.microservices.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProps {

    private String secret;
    private Map<String, Long> expiration;

}
