package org.lms.backend.calendar.dto;

import jakarta.validation.constraints.NotNull;
import org.lms.backend.calendar.entity.StudentAttendanceStatus;
import org.lms.backend.user.dto.UserDto;

public class StudentAttendanceDto {
    public record Record(
            @NotNull Long studentId,
            @NotNull StudentAttendanceStatus status,
            String notes
    ) {
    }

    public record Details(
            Long studentAttendanceRecordId,
            ClassSessionDto.Summary classSession,
            UserDto.Summary student,
            StudentAttendanceStatus status,
            UserDto.Summary markedBy,
            String notes
    ) {
    }
}
