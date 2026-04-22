package org.tutos.backend.classroom;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.tutos.backend.calendar.dto.ClassSessionDto;
import org.tutos.backend.classroom.dto.AnnouncementDto;
import org.tutos.backend.classroom.dto.AssignmentDto;
import org.tutos.backend.classroom.dto.ClassDto;
import org.tutos.backend.user.dto.UserDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClassroomController {
    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/groupclass/scheduled")
    public ClassDto.Details createScheduledGroupClass(@RequestBody @Valid ClassDto.ScheduledGroupClassCreation dto) {
        return classroomService.createScheduledGroupClass(dto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/privateclass/scheduled")
    public ClassDto.Details createScheduledPrivateClass(
            @RequestBody @Valid ClassDto.ScheduledPrivateClassCreation dto
    ) {
        return classroomService.createScheduledPrivateClass(dto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/groupclass/shortTerm")
    public ClassDto.Details createShortTermGroupClass(@RequestBody @Valid ClassDto.ShortTermGroupClassCreation dto) {
        return classroomService.createShortTermGroupClass(dto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/privateclass/shortTerm")
    public ClassDto.Details createShortTermPrivateClass(
            @RequestBody @Valid ClassDto.ShortTermPrivateClassCreation dto
    ) {
        return classroomService.createShortTermPrivateClass(dto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
    @GetMapping()
    public List<ClassDto.Details> getAllClasses() {
        return classroomService.getAllClasses();
    }

    @GetMapping("/{classId}")
    public ClassDto.Details getClassDetails(@PathVariable Long classId) {
        return classroomService.getClassDetails(classId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
    @PutMapping("/{classId}")
    public ClassDto.Details updateClassDetails(
            @PathVariable Long classId,
            @RequestBody @Valid ClassDto.DetailsUpdate classCreationDto
    ) {
        return classroomService.updateClassDetails(classId, classCreationDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
    @DeleteMapping("/{classId}")
    public void deleteClass(@PathVariable Long classId) {
        classroomService.deleteClass(classId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
    @PostMapping("/{classId}/tutors")
    public void addTutorToClass(@PathVariable Long classId, @RequestBody Long tutorId) {
        classroomService.addTutorToClass(classId, tutorId);
    }

    @GetMapping("/{classId}/tutors")
    public List<UserDto.Details> getClassTutors(@PathVariable Long classId) {
        return classroomService.getTutorsInClass(classId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
    @DeleteMapping("/{classId}/tutors")
    public void removeTutorFromClass(@PathVariable Long classId, @RequestBody Long tutorId) {
        classroomService.removeTutorFromClass(classId, tutorId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
    @PostMapping("/{classId}/students")
    public void addStudentToClass(@PathVariable Long classId, @RequestBody Long studentId) {
        classroomService.addStudentToClass(classId, studentId);
    }

    @GetMapping("/{classId}/students")
    public List<UserDto.Details> getClassStudents(@PathVariable Long classId) {
        return classroomService.getStudentsInClass(classId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
    @DeleteMapping("/{classId}/students")
    public void removeStudentFromClass(@PathVariable Long classId, @RequestBody Long studentId) {
        classroomService.removeStudentFromClass(classId, studentId);
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'MASTER')")
    @PostMapping("/{classId}/assignments")
    public void createAssignment(
            @PathVariable Long classId,
            @RequestBody @Valid AssignmentDto.Creation assignmentCreationDto
    ) {
        classroomService.createAssignment(classId, assignmentCreationDto);
    }

    @GetMapping("/{classId}/assignments")
    public List<AssignmentDto.Details> getAssignments(@PathVariable Long classId) {
        return classroomService.getAllClassAssignments(classId);
    }

    @GetMapping("/{classId}/assignments/{assignmentId}")
    public AssignmentDto.Details getAssignmentDetails(@PathVariable Long classId, @PathVariable Long assignmentId) {
        return classroomService.getAssignmentDetails(classId, assignmentId);
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'MASTER')")
    @PutMapping("/{classId}/assignments/{assignmentId}")
    public AssignmentDto.Details updateAssignmentDetails(
            @PathVariable Long classId,
            @PathVariable Long assignmentId,
            @RequestBody @Valid AssignmentDto.DetailsUpdate assignmentCreationDto
    ) {
        return classroomService.updateAssignmentDetails(classId, assignmentId, assignmentCreationDto);
    }

    @PreAuthorize("hasAnyRole('TEACHER, 'MASTER')")
    @DeleteMapping("/{classId}/assignments/{assignmentId}")
    public void deleteAssignment(@PathVariable Long classId, @PathVariable Long assignmentId) {
        classroomService.deleteAssignment(classId, assignmentId);
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'MASTER')")
    @PostMapping("/{classId}/announcements")
    public AnnouncementDto.Details createAnnouncement(
            @PathVariable Long classId,
            @RequestBody @Valid AnnouncementDto.Creation announcementCreationDto
    ) {
        return classroomService.createAnnouncement(classId, announcementCreationDto);
    }

    @GetMapping("/{classId}/announcements")
    public List<AnnouncementDto.Details> getAnnouncements(@PathVariable Long classId) {
        return classroomService.getAllClassAnnouncements(classId);
    }

    @GetMapping("/{classId}/announcements/{announcementId}")
    public AnnouncementDto.Details getAnnouncementDetails(
            @PathVariable Long classId,
            @PathVariable Long announcementId
    ) {
        return classroomService.getAnnouncementDetails(classId, announcementId);
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'MASTER')")
    @PutMapping("/{classId}/announcements/{announcementId}")
    public AnnouncementDto.Details updateAnnouncementDetails(
            @PathVariable Long classId,
            @PathVariable Long announcementId,
            @RequestBody @Valid AnnouncementDto.DetailsUpdate announcementCreationDto
    ) {
        return classroomService.updateAnnouncementDetails(classId, announcementId, announcementCreationDto);
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'MASTER')")
    @DeleteMapping("/{classId}/announcements/{announcementId}")
    public void deleteAnnouncement(@PathVariable Long classId, @PathVariable Long announcementId) {
        classroomService.deleteAnnouncement(classId, announcementId);
    }

    @GetMapping("/{classId}/sessions")
    public List<ClassSessionDto.Details> getClassSessions(
            @PathVariable Long classId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        return classroomService.getClassSessions(classId, startDate, endDate);
    }
}
