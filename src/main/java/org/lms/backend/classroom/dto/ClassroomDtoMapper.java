package org.lms.backend.classroom.dto;

import org.lms.backend.calendar.dto.CalendarDtoMapper;
import org.lms.backend.classroom.entity.Announcement;
import org.lms.backend.classroom.entity.Assignment;
import org.lms.backend.classroom.entity.Class;
import org.lms.backend.user.dto.UserDtoMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ClassroomDtoMapper {
    private final UserDtoMapper userDtoMapper;
    private final CalendarDtoMapper calendarDtoMapper;

    public ClassroomDtoMapper(CalendarDtoMapper calendarDtoMapper, UserDtoMapper userDtoMapper) {
        this.calendarDtoMapper = calendarDtoMapper;
        this.userDtoMapper = userDtoMapper;
    }

    public ClassDto.Details toDetailsDto(Class clazz) {
        if (clazz == null) return null;
        return new ClassDto.Details(
                clazz.getClassId(),
                clazz.getTitle(),
                clazz.getDescription(),
                clazz.getTutors().stream().map(userDtoMapper::toSummaryDto).collect(Collectors.toSet()),
                clazz.getStudents().stream().map(userDtoMapper::toSummaryDto).collect(Collectors.toSet()),
                clazz.getAssignments().stream().map(this::toSummaryDto).toList(),
                clazz.getAnnouncements().stream().map(this::toSummaryDto).toList(),
                calendarDtoMapper.toSummaryDto(clazz.getClassSchedule())
        );
    }

    public ClassDto.Summary toSummaryDto(Class clazz) {
        if (clazz == null) return null;
        return new ClassDto.Summary(
                clazz.getClassId(),
                clazz.getTitle(),
                clazz.getDescription(),
                calendarDtoMapper.toSummaryDto(clazz.getClassSchedule())
        );
    }

    public AssignmentDto.Details toDetailsDto(Assignment assignment) {
        if (assignment == null) return null;
        return new AssignmentDto.Details(
                assignment.getAssignmentId(),
                toSummaryDto(assignment.getClassBelongingTo()),
                assignment.getTitle(),
                assignment.getDescription(),
                assignment.getDueDate(),
                assignment.getTimePosted()
        );
    }

    public AssignmentDto.Summary toSummaryDto(Assignment assignment) {
        if (assignment == null) return null;
        return new AssignmentDto.Summary(
                assignment.getAssignmentId(),
                assignment.getTitle(),
                assignment.getDescription(),
                assignment.getDueDate()
        );
    }

    public AnnouncementDto.Details toDetailsDto(Announcement announcement) {
        if (announcement == null) return null;
        return new AnnouncementDto.Details(
                announcement.getAnnouncementId(),
                toSummaryDto(announcement.getClassBelongingTo()),
                announcement.getTitle(),
                announcement.getMessage(),
                announcement.getTimePosted()
        );
    }

    public AnnouncementDto.Summary toSummaryDto(Announcement announcement) {
        if (announcement == null) return null;
        return new AnnouncementDto.Summary(
                announcement.getAnnouncementId(),
                announcement.getTitle(),
                announcement.getMessage()
        );
    }
}
