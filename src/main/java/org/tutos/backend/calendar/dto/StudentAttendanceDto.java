package org.tutos.backend.calendar.dto;

import jakarta.validation.constraints.NotNull;
import org.tutos.backend.calendar.entity.StudentAttendanceStatus;
import org.tutos.backend.user.dto.UserDto;

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
