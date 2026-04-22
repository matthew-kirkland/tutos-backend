package org.tutos.backend.calendar;

import org.springframework.security.access.prepost.PreAuthorize;
import org.tutos.backend.calendar.dto.ClassSessionDto;
import org.tutos.backend.calendar.entity.ClassSessionStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/calendar")
public class ClassCalendarController {
    private final ClassCalendarService classCalendarService;

    public ClassCalendarController(ClassCalendarService classCalendarService) {
        this.classCalendarService = classCalendarService;
    }

    @GetMapping("/sessions/{classSessionId}")
    public ClassSessionDto.Details getSessionDetails(@PathVariable UUID classSessionId) {
        return classCalendarService.getSessionDetails(classSessionId);
    }

    @GetMapping("/sessions/{classSessionId}/status")
    public ClassSessionStatus getSessionStatus(@PathVariable UUID classSessionId) {
        return classCalendarService.getSessionStatus(classSessionId);
    }

    @PutMapping("/sessions/{classSessionId}/status")
    public ClassSessionDto.Details updateSessionStatus(
            @PathVariable UUID classSessionId,
            @RequestBody ClassSessionStatus status
    ) {
        return classCalendarService.updateSessionStatus(classSessionId, status);
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'MASTER')")
    @PostMapping("/sessions/{classSessionId}/addNotes")
    public void addSessionNotes(@PathVariable UUID classSessionId, @RequestBody String notes) {
        classCalendarService.addSessionNotes(classSessionId, notes);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
    @PutMapping("/sessions/{classSessionId}/replaceTutors")
    public void replaceSessionTutors(@PathVariable UUID classSessionId, @RequestBody Set<UUID> tutorIds) {
        classCalendarService.replaceSessionTutors(classSessionId, tutorIds);
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'MASTER')")
    @PostMapping("/sessions/{classSessionId}/attendance")
    public ClassSessionDto.AttendanceDetails submitSessionAttendance(
            @PathVariable UUID classSessionId,
            @RequestBody ClassSessionDto.AttendanceRequest request,
            @RequestBody UUID tutorId
    ) {
        return classCalendarService.submitSessionAttendance(classSessionId, request, tutorId);
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'MASTER')")
    @GetMapping("/sessions/{classSessionId}/attendance")
    public ClassSessionDto.AttendanceDetails getSessionAttendance(@PathVariable UUID classSessionId) {
        return classCalendarService.getSessionAttendance(classSessionId);
    }
}
