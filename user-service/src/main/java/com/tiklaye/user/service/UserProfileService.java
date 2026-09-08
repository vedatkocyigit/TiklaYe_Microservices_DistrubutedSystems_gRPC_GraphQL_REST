package com.tiklaye.user.service;

import com.tiklaye.common.exception.ApiException;
import com.tiklaye.user.domain.UserProfile;
import com.tiklaye.user.dto.UpdateProfileRequest;
import com.tiklaye.user.dto.UserProfileResponse;
import com.tiklaye.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Transactional(readOnly = true)
    public UserProfileResponse getMyProfile(UUID userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseGet(() -> createEmptyProfile(userId));

        return toResponse(profile);
    }

    @Transactional
    public UserProfileResponse updateMyProfile(UUID userId, UpdateProfileRequest request) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseGet(() -> createEmptyProfile(userId));

        profile.setFirstName(request.firstName());
        profile.setLastName(request.lastName());
        profile.setPhoneNumber(request.phoneNumber());

        UserProfile saved = userProfileRepository.save(profile);
        return toResponse(saved);
    }

    private UserProfile createEmptyProfile(UUID userId) {
        UserProfile profile = UserProfile.builder()
                .userId(userId)
                .build();
        return userProfileRepository.save(profile);
    }

    private UserProfileResponse toResponse(UserProfile profile) {
        return new UserProfileResponse(
                profile.getUserId(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getPhoneNumber(),
                profile.getAvatarUrl()
        );
    }
}