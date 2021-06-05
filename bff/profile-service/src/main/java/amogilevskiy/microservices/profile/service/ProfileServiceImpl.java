package amogilevskiy.microservices.profile.service;

import amogilevskiy.microservices.profile.domain.AuthUser;
import amogilevskiy.microservices.profile.domain.Profile;
import amogilevskiy.microservices.profile.dto.CreateProfileRequestDto;
import amogilevskiy.microservices.profile.dto.ProfileResponseDto;
import amogilevskiy.microservices.profile.dto.UpdateProfileRequestDto;
import amogilevskiy.microservices.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public ProfileResponseDto findById(long id, AuthUser user) {
        if (user.getId() != id) {
            throw new ForbiddenException();
        }
        return profileRepository.findById(id)
                .map(profileMapper::toResponseDto)
                .orElseThrow(ProfileNotFoundException::new);
    }

    @Override
    public ProfileResponseDto createById(long id, AuthUser user, CreateProfileRequestDto dto) {
        if (user.getId() != id) {
            throw new ForbiddenException();
        }
        var profile = Profile.builder()
                .id(id)
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
        return profileMapper.toResponseDto(profileRepository.save(profile));
    }

    @Override
    public ProfileResponseDto updateById(long id, AuthUser user, UpdateProfileRequestDto dto) {
        if (user.getId() != id) {
            throw new ForbiddenException();
        }

        return profileRepository.findById(id).map(profile -> {
            if (!dto.getFirstName().isBlank()) {
                profile.setFirstName(dto.getFirstName());
            }
            if (!dto.getLastName().isBlank()) {
                profile.setLastName(dto.getLastName());
            }
            return profileRepository.save(profile);
        }).map(profileMapper::toResponseDto).orElseThrow();
    }
}
