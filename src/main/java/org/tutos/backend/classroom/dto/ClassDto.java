package org.tutos.backend.classroom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.tutos.backend.calendar.dto.ClassScheduleDto;
import org.tutos.backend.calendar.dto.ClassSessionDto;
import org.tutos.backend.user.dto.UserDto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ClassDto {
    public record ScheduledGroupClassCreation(
            @NotBlank String title,
            String description,
            @NotNull ClassScheduleDto.Creation scheduleCreationDto,
            Set<UUID> tutorIds,
            Set<UUID> studentIds
    ) {
    }

    public record ScheduledPrivateClassCreation(
            @NotBlank String title,
            String description,
            @NotNull ClassScheduleDto.Creation scheduleCreationDto,
            Set<UUID> tutorIds,
            UUID studentId
    ) {
    }

    public record ShortTermGroupClassCreation(
            @NotBlank String title,
            String description,
            @NotNull List<ClassSessionDto.Creation> sessionDates,
            Set<UUID> tutorIds,
            Set<UUID> studentIds
    ) {
    }

    public record ShortTermPrivateClassCreation(
            @NotBlank String title,
            String description,
            @NotNull List<ClassSessionDto.Creation> sessionDates,
            Set<UUID> tutorIds,
            UUID studentId
    ) {
    }

    public record Details(
            UUID classId,
            String title,
            String description,
            Set<UserDto.Summary> tutors,
            Set<UserDto.Summary> students,
            List<AssignmentDto.Summary> assignments,
            List<AnnouncementDto.Summary> announcements,
            ClassScheduleDto.Summary scheduleDetailsDto
    ) {
    }

    public record Summary(
            UUID classId,
            String title,
            String description,
            ClassScheduleDto.Summary scheduleDetailsDto
    ) {
    }

    public record DetailsUpdate(
            @NotBlank String title,
            String description,
            @NotNull DayOfWeek dayOfWeek,
            @NotNull LocalTime startTime,
            @Positive @NotNull Long durationOfMinutes
    ) {
    }
}
