package amogilevskiy.microservices.auth.service;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("user_already_exists");
    }

}