package org.tutos.backend.calendar.dto;

import org.tutos.backend.calendar.entity.ClassSchedule;
import org.tutos.backend.calendar.entity.ClassSession;
import org.tutos.backend.calendar.entity.StudentAttendanceRecord;
import org.tutos.backend.user.dto.UserDtoMapper;
import org.springframework.stereotype.Component;

@Component
public class CalendarDtoMapper {
    private final UserDtoMapper userDtoMapper;

    public CalendarDtoMapper(UserDtoMapper userDtoMapper) {
        this.userDtoMapper = userDtoMapper;
    }

    public ClassScheduleDto.Details toDetailsDto(ClassSchedule schedule) {
        if (schedule == null) return null;
        return new ClassScheduleDto.Details(
                schedule.getClassScheduleId(),
                schedule.getClassOf().getClassId(),
                schedule.getDayOfWeek(),
                schedule.getStartTime(),
                schedule.getDuration(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                schedule.getRecurrenceType()
        );
    }

    public ClassScheduleDto.Summary toSummaryDto(ClassSchedule schedule) {
        if (schedule == null) return null;
        return new ClassScheduleDto.Summary(
                schedule.getClassScheduleId(),
                schedule.getDayOfWeek(),
                schedule.getStartTime(),
                schedule.getDuration(),
                schedule.getRecurrenceType()
        );
    }

    public ClassSessionDto.Details toDetailsDto(ClassSession session) {
        if (session == null) return null;
        return new ClassSessionDto.Details(
                session.getClassSessionId(),
                session.getClassOf().getClassId(),
                (session.getClassSchedule() == null) ? null : session.getClassSchedule().getClassScheduleId(),
                session.getStartTime(),
                session.getEndTime(),
                session.getStatus(),
                session.getNotes()
        );
    }

    public ClassSessionDto.Summary toSummaryDto(ClassSession session) {
        if (session == null) return null;
        return new ClassSessionDto.Summary(
                session.getClassSessionId(),
                session.getStartTime(),
                session.getEndTime(),
                session.getStatus()
        );
    }

    public ClassSessionDto.AttendanceDetails toAttendanceDetailsDto(ClassSession session) {
        if (session == null) return null;
        return new ClassSessionDto.AttendanceDetails(
                session.getClassSessionId(),
                session.getAttendanceRecords().stream().map(this::toDetailsDto).toList()
        );
    }

    public StudentAttendanceDto.Details toDetailsDto(StudentAttendanceRecord record) {
        if (record == null) return null;
        return new StudentAttendanceDto.Details(
                record.getStudentAttendanceRecordId(),
                toSummaryDto(record.getClassSession()),
                userDtoMapper.toSummaryDto(record.getStudent()),
                record.getStatus(),
                userDtoMapper.toSummaryDto(record.getMarkedBy()),
                record.getNotes()
        );
    }
}
