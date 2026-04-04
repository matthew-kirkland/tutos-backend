package org.lms.backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record ParentCreationDto(
        @Email @NotBlank String email,
        @NotBlank String password,
        @Pattern(regexp = "^\\+[1-9][0-9]{3,14}$") String phone,
        @NotBlank String nameFirst,
        @NotBlank String nameLast,
        LocalDate dob
) {
}
