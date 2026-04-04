package org.tutos.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginInputDto(
        @NotBlank String username,
        @NotBlank String password
) {
}
