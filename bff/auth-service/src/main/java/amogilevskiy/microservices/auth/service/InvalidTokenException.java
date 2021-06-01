package amogilevskiy.microservices.auth.service;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("invalid_token");
    }
}
