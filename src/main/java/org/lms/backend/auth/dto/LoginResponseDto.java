package org.lms.backend.auth.dto;

import org.lms.backend.user.entity.User;

public record LoginResponseDto(
        User user,
        String jwt
) {
}
