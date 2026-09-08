package com.tiklaye.user.controller;

import com.tiklaye.common.security.TiklayeUserPrincipal;
import com.tiklaye.user.dto.UpdateProfileRequest;
import com.tiklaye.user.dto.UserProfileResponse;
import com.tiklaye.user.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/me")
    public UserProfileResponse getMyProfile(@AuthenticationPrincipal TiklayeUserPrincipal principal) {
        return userProfileService.getMyProfile(principal.getUserId());
    }

    @PutMapping("/me")
    public UserProfileResponse updateMyProfile(@AuthenticationPrincipal TiklayeUserPrincipal principal,
                                               @Valid @RequestBody UpdateProfileRequest request) {
        return userProfileService.updateMyProfile(principal.getUserId(), request);
    }
}