package amogilevskiy.microservices.profile.service;

import amogilevskiy.microservices.profile.domain.Profile;
import amogilevskiy.microservices.profile.dto.ProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileMapperImpl implements ProfileMapper {

    private final ModelMapper modelMapper;

    @Override
    public ProfileResponseDto toResponseDto(Profile profile) {
        return modelMapper.map(profile, ProfileResponseDto.class);
    }

}
