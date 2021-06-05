package amogilevskiy.microservices.profile.controller;

import amogilevskiy.microservices.profile.domain.AuthUser;
import amogilevskiy.microservices.profile.dto.CreateProfileRequestDto;
import amogilevskiy.microservices.profile.dto.ProfileResponseDto;
import amogilevskiy.microservices.profile.dto.UpdateProfileRequestDto;
import amogilevskiy.microservices.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/1.0/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{id}")
    public ProfileResponseDto findById(@PathVariable long id, @RequestHeader(value = "X-AUTH-USER-ID") long authUserId) {
        return profileService.findById(id, new AuthUser(authUserId));
    }

    @PostMapping("/{id}")
    public ProfileResponseDto createById(@PathVariable long id,
                                         @RequestHeader(value = "X-AUTH-USER-ID") long authUserId,
                                         @RequestBody CreateProfileRequestDto dto) {
        return profileService.createById(id, new AuthUser(authUserId), dto);
    }

    @PatchMapping("/{id}")
    public ProfileResponseDto updatedById(@PathVariable long id,
                                         @RequestHeader(value = "X-AUTH-USER-ID") long authUserId,
                                         @RequestBody UpdateProfileRequestDto dto) {
        return profileService.updateById(id, new AuthUser(authUserId), dto);
    }

}
