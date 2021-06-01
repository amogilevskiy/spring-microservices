package amogilevskiy.microservices.profile.service;

public class ProfileNotFoundException extends RuntimeException {

    public ProfileNotFoundException() {
        super("profile_not_found");
    }
}
