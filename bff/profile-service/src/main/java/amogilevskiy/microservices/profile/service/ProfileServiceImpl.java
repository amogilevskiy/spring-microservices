package amogilevskiy.microservices.profile.service;

import amogilevskiy.microservices.profile.domain.AuthUser;
import amogilevskiy.microservices.profile.dto.ProfileResponseDto;
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

}
