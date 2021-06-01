package amogilevskiy.microservices.profile.service;

import amogilevskiy.microservices.profile.domain.AuthUser;
import amogilevskiy.microservices.profile.dto.ProfileResponseDto;

public interface ProfileService {

    ProfileResponseDto findById(long id, AuthUser user);

}
