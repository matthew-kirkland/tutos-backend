package org.tutos.backend.calendar;

import org.tutos.backend.calendar.dto.ClassSessionDto;
import org.tutos.backend.calendar.entity.ClassSessionStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class ClassCalendarController {
    private final ClassCalendarService classCalendarService;

    public ClassCalendarController(ClassCalendarService classCalendarService) {
        this.classCalendarService = classCalendarService;
    }

    @GetMapping("/sessions/{classSessionId}")
    public ClassSessionDto.Details getSessionDetails(@PathVariable Long classSessionId) {
        return classCalendarService.getSessionDetails(classSessionId);
    }

    @GetMapping("/sessions/{classSessionId}/status")
    public ClassSessionStatus getSessionStatus(@PathVariable Long classSessionId) {
        return classCalendarService.getSessionStatus(classSessionId);
    }

    @PutMapping("/sessions/{classSessionId}/status")
    public ClassSessionDto.Details updateSessionStatus(
            @PathVariable Long classSessionId,
            @RequestBody ClassSessionStatus status
    ) {
        return classCalendarService.updateSessionStatus(classSessionId, status);
    }

    @PostMapping("/sessions/{classSessionId}/addNotes")
    public void addSessionNotes(@PathVariable Long classSessionId, @RequestBody String notes) {
        classCalendarService.addSessionNotes(classSessionId, notes);
    }

    @PutMapping("/sessions/{classSessionId}/replaceTutors")
    public void replaceSessionTutors(@PathVariable Long classSessionId, @RequestBody Set<Long> tutorIds) {
        classCalendarService.replaceSessionTutors(classSessionId, tutorIds);
    }

    @PostMapping("/sessions/{classSessionId}/attendance")
    public ClassSessionDto.AttendanceDetails submitSessionAttendance(
            @PathVariable Long classSessionId,
            @RequestBody ClassSessionDto.AttendanceRequest request,
            @RequestBody Long tutorId
    ) {
        return classCalendarService.submitSessionAttendance(classSessionId, request, tutorId);
    }

    @GetMapping("/sessions/{classSessionId}/attendance")
    public ClassSessionDto.AttendanceDetails getSessionAttendance(@PathVariable Long classSessionId) {
        return classCalendarService.getSessionAttendance(classSessionId);
    }
}
