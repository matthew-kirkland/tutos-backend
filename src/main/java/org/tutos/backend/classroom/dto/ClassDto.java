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

public class ClassDto {
    public record ScheduledGroupClassCreation(
            @NotBlank String title,
            String description,
            @NotNull ClassScheduleDto.Creation scheduleCreationDto,
            Set<Long> tutorIds,
            Set<Long> studentIds
    ) {
    }

    public record ScheduledPrivateClassCreation(
            @NotBlank String title,
            String description,
            @NotNull ClassScheduleDto.Creation scheduleCreationDto,
            Set<Long> tutorIds,
            Long studentId
    ) {
    }

    public record ShortTermGroupClassCreation(
            @NotBlank String title,
            String description,
            @NotNull List<ClassSessionDto.Creation> sessionDates,
            Set<Long> tutorIds,
            Set<Long> studentIds
    ) {
    }

    public record ShortTermPrivateClassCreation(
            @NotBlank String title,
            String description,
            @NotNull List<ClassSessionDto.Creation> sessionDates,
            Set<Long> tutorIds,
            Long studentId
    ) {
    }

    public record Details(
            Long classId,
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
            Long classId,
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
