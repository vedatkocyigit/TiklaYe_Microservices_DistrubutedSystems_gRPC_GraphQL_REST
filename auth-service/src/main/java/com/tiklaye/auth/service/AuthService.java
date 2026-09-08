package com.tiklaye.auth.service;

import com.tiklaye.common.security.Role;
import com.tiklaye.auth.domain.User;
import com.tiklaye.auth.dto.LoginRequest;
import com.tiklaye.auth.dto.LoginResponse;
import com.tiklaye.auth.dto.RegisterRequest;
import com.tiklaye.auth.dto.RegisterResponse;
import com.tiklaye.auth.repository.UserRepository;
import com.tiklaye.common.security.JwtService;
import com.tiklaye.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ApiException(HttpStatus.CONFLICT, "EMAIL_ALREADY_EXISTS", "Bu email zaten kayıtlı");
        }

        User user = User.builder()
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(Role.CUSTOMER)
                .build();

        User saved = userRepository.save(user);

        return new RegisterResponse(saved.getId(), saved.getEmail(), saved.getRole().name());
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", "Email veya şifre hatalı"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", "Email veya şifre hatalı");
        }

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getEmail(), user.getRole().name());
        String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getEmail(), user.getRole().name());

        return new LoginResponse(accessToken, refreshToken, "Bearer", jwtService.getAccessTokenExpirationSeconds());
    }
}