package com.tiklaye.common.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ErrorResponse {
    private String traceId;
    private String path;
    private String message;
    private String error;
    private int status;
    private Instant timestamp;
}
