package org.tutos.backend.calendar.dto;

import jakarta.validation.constraints.NotNull;
import org.tutos.backend.calendar.entity.StudentAttendanceStatus;
import org.tutos.backend.user.dto.UserDto;

import java.util.UUID;

public class StudentAttendanceDto {
    public record Record(
            @NotNull UUID studentId,
            @NotNull StudentAttendanceStatus status,
            String notes
    ) {
    }

    public record Details(
            UUID studentAttendanceRecordId,
            ClassSessionDto.Summary classSession,
            UserDto.Summary student,
            StudentAttendanceStatus status,
            UserDto.Summary markedBy,
            String notes
    ) {
    }
}
