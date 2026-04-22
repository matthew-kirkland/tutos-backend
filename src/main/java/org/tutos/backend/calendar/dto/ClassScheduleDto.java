package org.tutos.backend.calendar.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.tutos.backend.calendar.entity.RecurrenceType;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class ClassScheduleDto {
    public record Creation(
            @NotNull DayOfWeek dayOfWeek,
            @NotNull LocalTime startTime,
            @Positive @NotNull Long durationOfMinutes,
            @NotNull LocalDate startDate,
            LocalDate endDate,
            RecurrenceType recurrenceType
    ) {
    }

    public record Details(
            UUID classScheduleId,
            UUID classOfId,
            DayOfWeek dayOfWeek,
            LocalTime startTime,
            Duration duration,
            LocalDate startDate,
            LocalDate endDate,
            RecurrenceType recurrenceType
    ) {
    }

    public record Summary(
            UUID classScheduleId,
            DayOfWeek dayOfWeek,
            LocalTime startTime,
            Duration duration,
            RecurrenceType recurrenceType
    ) {
    }

    public record DetailsUpdate(
            UUID classScheduleId,
            DayOfWeek dayOfWeek,
            LocalTime startTime,
            Duration duration,
            LocalDate startDate,
            LocalDate endDate,
            RecurrenceType recurrenceType
    ) {
    }
}
