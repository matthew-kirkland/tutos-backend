package org.lms.backend.calendar.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.lms.backend.calendar.entity.RecurrenceType;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

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
            Long classScheduleId,
            Long classOfId,
            DayOfWeek dayOfWeek,
            LocalTime startTime,
            Duration duration,
            LocalDate startDate,
            LocalDate endDate,
            RecurrenceType recurrenceType
    ) {
    }

    public record Summary(
            Long classScheduleId,
            DayOfWeek dayOfWeek,
            LocalTime startTime,
            Duration duration,
            RecurrenceType recurrenceType
    ) {
    }

    public record DetailsUpdate(
            Long classScheduleId,
            DayOfWeek dayOfWeek,
            LocalTime startTime,
            Duration duration,
            LocalDate startDate,
            LocalDate endDate,
            RecurrenceType recurrenceType
    ) {
    }
}
