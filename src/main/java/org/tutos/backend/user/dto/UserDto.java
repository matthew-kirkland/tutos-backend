package org.tutos.backend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class UserDto {
    public record Details(
            Long userId,
            String username,
            String email,
            String phone,
            String nameFirst,
            String nameLast,
            LocalDate dob
    ) {
    }

    public record Summary(
            Long userId,
            String email,
            String phone,
            String nameFirst,
            String nameLast
    ) {
    }

    public record DetailsUpdate(
            @Email @NotBlank String email,
            @Pattern(regexp = "^\\+[1-9][0-9]{3,14}$") String phone,
            @NotBlank String nameFirst,
            @NotBlank String nameLast,
            LocalDate dob
    ) {
    }
}
