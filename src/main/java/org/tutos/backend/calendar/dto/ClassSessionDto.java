package org.tutos.backend.calendar.dto;

import jakarta.validation.constraints.NotNull;
import org.tutos.backend.calendar.entity.ClassSessionStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ClassSessionDto {
    public record Creation(
            @NotNull LocalDateTime startTime,
            @NotNull LocalDateTime endTime
    ) {
    }

    public record Details(
            Long classSessionId,
            Long classOfId,
            Long classScheduleId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            ClassSessionStatus status,
            String notes
    ) {
    }

    public record Summary(
            Long classSessionId,
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
            Long classSessionId,
            List<StudentAttendanceDto.Details> attendance
    ) {
    }
}
