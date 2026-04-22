package org.tutos.backend.calendar.dto;

import jakarta.validation.constraints.NotNull;
import org.tutos.backend.calendar.entity.ClassSessionStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ClassSessionDto {
    public record Creation(
            @NotNull LocalDateTime startTime,
            @NotNull LocalDateTime endTime
    ) {
    }

    public record Details(
            UUID classSessionId,
            UUID classOfId,
            UUID classScheduleId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            ClassSessionStatus status,
            String notes
    ) {
    }

    public record Summary(
            UUID classSessionId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            ClassSessionStatus status
    ) {
    }

    public record AttendanceRequest(
            List<StudentAttendanceDto.Record> attendanceRecords
    ) {
    }

    public record AttendanceDetails(
            UUID classSessionId,
            List<StudentAttendanceDto.Details> attendance
    ) {
    }
}
