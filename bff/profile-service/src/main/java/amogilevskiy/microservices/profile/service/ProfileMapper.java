package amogilevskiy.microservices.profile.service;

import amogilevskiy.microservices.profile.domain.Profile;
import amogilevskiy.microservices.profile.dto.ProfileResponseDto;

public interface ProfileMapper {

    ProfileResponseDto toResponseDto(Profile profile);

}
