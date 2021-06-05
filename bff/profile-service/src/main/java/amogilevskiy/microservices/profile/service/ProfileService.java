package amogilevskiy.microservices.profile.service;

import amogilevskiy.microservices.profile.domain.AuthUser;
import amogilevskiy.microservices.profile.dto.CreateProfileRequestDto;
import amogilevskiy.microservices.profile.dto.ProfileResponseDto;
import amogilevskiy.microservices.profile.dto.UpdateProfileRequestDto;

public interface ProfileService {

    ProfileResponseDto findById(long id, AuthUser user);

    ProfileResponseDto createById(long id, AuthUser user, CreateProfileRequestDto dto);

    ProfileResponseDto updateById(long id, AuthUser user, UpdateProfileRequestDto dto);

}
