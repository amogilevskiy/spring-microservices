package amogilevskiy.microservices.auth.service;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("invalid_credentials");
    }

}
