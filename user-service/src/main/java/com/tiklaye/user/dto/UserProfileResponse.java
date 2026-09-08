package com.tiklaye.user.dto;

import java.util.UUID;

public record UserProfileResponse(
        UUID userId,
        String firstName,
        String lastName,
        String phoneNumber,
        String avatarUrl
) {}