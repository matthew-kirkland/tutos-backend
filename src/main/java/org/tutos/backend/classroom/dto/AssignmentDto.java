package org.tutos.backend.classroom.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public class AssignmentDto {
    public record Creation(
            @NotBlank String title,
            @NotBlank String description,
            @Future LocalDateTime dueDate
    ) {
    }

    public record Details(
            UUID assignmentId,
            ClassDto.Summary classBelongingTo,
            String title,
            String description,
            LocalDateTime dueDate,
            LocalDateTime timePosted
    ) {
    }

    public record Summary(
            UUID assignmentId,
            String title,
            String description,
            LocalDateTime dueDate
    ) {
    }

    public record DetailsUpdate(
            @NotBlank String title,
            @NotBlank String description,
            @Future LocalDateTime dueDate
    ) {
    }
}
