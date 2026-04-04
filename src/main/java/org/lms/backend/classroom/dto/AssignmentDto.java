package org.lms.backend.classroom.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class AssignmentDto {
    public record Creation(
            @NotBlank String title,
            @NotBlank String description,
            @Future LocalDateTime dueDate
    ) {
    }

    public record Details(
            Long assignmentId,
            ClassDto.Summary classBelongingTo,
            String title,
            String description,
            LocalDateTime dueDate,
            LocalDateTime timePosted
    ) {
    }

    public record Summary(
            Long assignmentId,
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
