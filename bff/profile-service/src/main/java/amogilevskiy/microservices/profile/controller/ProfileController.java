package amogilevskiy.microservices.profile.controller;

import amogilevskiy.microservices.profile.domain.AuthUser;
import amogilevskiy.microservices.profile.dto.ProfileResponseDto;
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

}
