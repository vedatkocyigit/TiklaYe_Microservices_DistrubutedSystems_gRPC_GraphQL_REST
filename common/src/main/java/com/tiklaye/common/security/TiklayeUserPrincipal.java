package com.tiklaye.common.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class TiklayeUserPrincipal {
    private final UUID userId;
    private final String email;
    private final Role role;
}