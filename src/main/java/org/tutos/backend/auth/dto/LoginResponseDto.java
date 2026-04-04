package org.tutos.backend.auth.dto;

import org.tutos.backend.user.entity.User;

public record LoginResponseDto(
        User user,
        String jwt
) {
}
