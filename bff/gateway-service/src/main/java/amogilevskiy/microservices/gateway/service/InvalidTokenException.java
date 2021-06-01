package amogilevskiy.microservices.gateway.service;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("invalid_token");
    }
}
