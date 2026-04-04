package org.tutos.backend.common.dto;

import java.time.Instant;

public record ErrorMessageDto(
        int status,
        String error,
        String message,
        String path,
        Instant timestamp
) {
    public ErrorMessageDto(int status, String error, String message, String path) {
        this(status, error, message, path, Instant.now());
    }
}
