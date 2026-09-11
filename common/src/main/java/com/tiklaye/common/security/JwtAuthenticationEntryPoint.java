package com.tiklaye.common.security;

import com.tiklaye.common.correlation.CorrelationIdFilter;
import com.tiklaye.common.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws java.io.IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        ErrorResponse body = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message("Kimlik doğrulama gerekli")
                .path(request.getRequestURI())
                .traceId(org.slf4j.MDC.get(CorrelationIdFilter.MDC_KEY))
                .build();

        objectMapper.writeValue(response.getWriter(), body);
    }
}