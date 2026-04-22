package org.tutos.backend.classroom.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public class AnnouncementDto {
    public record Creation(
            @NotBlank String title,
            @NotBlank String message
    ) {
    }

    public record Details(
            UUID announcementId,
            ClassDto.Summary classBelongingTo,
            String title,
            String message,
            LocalDateTime timePosted
    ) {
    }

    public record Summary(
            UUID announcementId,
            String title,
            String message
    ) {
    }

    public record DetailsUpdate(
            @NotBlank String title,
            @NotBlank String message
    ) {
    }
}
