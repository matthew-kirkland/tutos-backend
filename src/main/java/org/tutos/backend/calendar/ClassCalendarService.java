package org.tutos.backend.calendar;

import org.tutos.backend.calendar.dto.CalendarDtoMapper;
import org.tutos.backend.calendar.dto.ClassScheduleDto;
import org.tutos.backend.calendar.dto.ClassSessionDto;
import org.tutos.backend.calendar.dto.StudentAttendanceDto;
import org.tutos.backend.calendar.entity.*;
import org.tutos.backend.calendar.entity.*;
import org.tutos.backend.calendar.exception.ClassSessionNotFoundException;
import org.tutos.backend.classroom.entity.Class;
import org.tutos.backend.classroom.exception.UserNotInClassException;
import org.tutos.backend.common.exception.InvalidUserTypeException;
import org.tutos.backend.user.UserRepository;
import org.tutos.backend.user.entity.Student;
import org.tutos.backend.user.entity.Tutor;
import org.tutos.backend.user.entity.User;
import org.tutos.backend.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ClassCalendarService {
    private final ClassCalendarRepository classCalendarRepository;
    private final ClassSessionRepository classSessionRepository;
    private final UserRepository userRepository;

    private final CalendarDtoMapper calendarDtoMapper;

    public ClassCalendarService(
            ClassCalendarRepository classCalendarRepository,
            ClassSessionRepository classSessionRepository,
            UserRepository userRepository,
            CalendarDtoMapper calendarDtoMapper
    ) {
        this.classCalendarRepository = classCalendarRepository;
        this.classSessionRepository = classSessionRepository;
        this.userRepository = userRepository;
        this.calendarDtoMapper = calendarDtoMapper;
    }

    public ClassSessionDto.Details getSessionDetails(UUID classSessionId) {
        ClassSession session = classSessionRepository.findById(classSessionId)
                .orElseThrow(() -> new ClassSessionNotFoundException(classSessionId));
        return calendarDtoMapper.toDetailsDto(session);
    }

    public ClassSessionStatus getSessionStatus(UUID classSessionId) {
        ClassSession session = classSessionRepository.findById(classSessionId)
                .orElseThrow(() -> new ClassSessionNotFoundException(classSessionId));
        return session.getStatus();
    }

    public ClassSessionDto.Details updateSessionStatus(UUID classSessionId, ClassSessionStatus status) {
        ClassSession session = classSessionRepository.findById(classSessionId)
                .orElseThrow(() -> new ClassSessionNotFoundException(classSessionId));
        session.setStatus(status);
        return calendarDtoMapper.toDetailsDto(classSessionRepository.save(session));
    }

    public void addSessionNotes(UUID classSessionId, String notes) {
        ClassSession session = classSessionRepository.findById(classSessionId)
                .orElseThrow(() -> new ClassSessionNotFoundException(classSessionId));
        session.setNotes(notes);
    }

    @Transactional
    public void replaceSessionTutors(UUID classSessionId, Set<UUID> tutorIds) {
        List<User> tutors = userRepository.findAllById(tutorIds);
        // TODO throw exception if any user not found
        ClassSession existingSession = classSessionRepository.findById(classSessionId)
                .orElseThrow(() -> new ClassSessionNotFoundException(classSessionId));

        Set<Tutor> tutorsTyped = new HashSet<Tutor>();
        for (User user : tutors) {
            if (!(user instanceof Tutor tutor)) {
                throw new InvalidUserTypeException("User " + user.getUserId() + " is not a tutor");
            }
            tutorsTyped.add(tutor);
        }

        existingSession.setReplacementTutors(tutorsTyped);
        classSessionRepository.save(existingSession);
    }

    @Transactional
    public ClassSessionDto.AttendanceDetails submitSessionAttendance(
            UUID classSessionId,
            ClassSessionDto.AttendanceRequest request,
            UUID tutorId
    ) {
        ClassSession existingSession = classSessionRepository.findById(classSessionId)
                .orElseThrow(() -> new ClassSessionNotFoundException(classSessionId));

        Set<StudentAttendanceRecord> attendanceRecords = new HashSet<StudentAttendanceRecord>();
        for (StudentAttendanceDto.Record attendanceRecord : request.attendanceRecords()) {
            User existingStudent = userRepository.findById(attendanceRecord.studentId())
                    .orElseThrow(() -> new UserNotFoundException(attendanceRecord.studentId()));
            if (!(existingStudent instanceof Student s)) {
                throw new InvalidUserTypeException("User with id " + attendanceRecord.studentId() + " is not a student");
            }
            if (!existingSession.getStudents().contains(s)) {
                throw new UserNotInClassException(
                        "Student with id " + attendanceRecord.studentId() + " does not attend session with id " + classSessionId
                );
            }

            User existingTutor = userRepository.findById(tutorId)
                    .orElseThrow(() -> new UserNotFoundException(tutorId));
            if (!(existingTutor instanceof Tutor t)) {
                throw new InvalidUserTypeException("User with id " + tutorId + " is not a tutor");
            }
            if (!existingSession.getActiveTutors().contains(t)) {
                throw new UserNotInClassException(
                        "Tutor with id " + tutorId + " does not teach session with id " + classSessionId
                );
            }

            StudentAttendanceRecord newAttendanceRecord = new StudentAttendanceRecord(existingSession, s, t);
            attendanceRecords.add(newAttendanceRecord);
        }
        existingSession.setAttendanceRecords(attendanceRecords);
        return calendarDtoMapper.toAttendanceDetailsDto(classSessionRepository.save(existingSession));
    }

    public ClassSessionDto.AttendanceDetails getSessionAttendance(UUID classSessionId) {
        ClassSession existingSession = classSessionRepository.findById(classSessionId)
                .orElseThrow(() -> new ClassSessionNotFoundException(classSessionId));
        return calendarDtoMapper.toAttendanceDetailsDto(existingSession);
    }

    @Transactional
    public ClassScheduleDto.Details createClassSchedule(Class classOf, ClassScheduleDto.Creation dto) {
        ClassSchedule schedule = new ClassSchedule(
                classOf,
                dto.dayOfWeek(),
                dto.startTime(),
                Duration.ofMinutes(dto.durationOfMinutes()),
                dto.startDate(),
                dto.endDate(),
                dto.recurrenceType()
        );
        classOf.setClassSchedule(schedule);
        generateScheduleSessions(classOf, schedule);
        return calendarDtoMapper.toDetailsDto(classCalendarRepository.save(schedule));
    }

    @Transactional
    public void generateShortTermSessions(Class classOf, List<ClassSessionDto.Creation> sessionDates) {
        for (ClassSessionDto.Creation dto : sessionDates) {
            ClassSession newSession = new ClassSession(
                    classOf,
                    null,
                    dto.startTime(),
                    dto.endTime(),
                    classOf.getTutors(),
                    classOf.getStudents(),
                    ClassSessionStatus.SCHEDULED
            );
            classSessionRepository.save(newSession);
        }
        classOf.setClassSchedule(null);
    }

    private void generateScheduleSessions(Class classOf, ClassSchedule schedule) {
        List<ClassSessionDto.Creation> sessionDtos = calculateSessionTimes(schedule);

        for (ClassSessionDto.Creation dto : sessionDtos) {
            ClassSession newSession = new ClassSession(
                    classOf,
                    schedule,
                    dto.startTime(),
                    dto.endTime(),
                    classOf.getTutors(),
                    classOf.getStudents(),
                    ClassSessionStatus.SCHEDULED
            );
            classSessionRepository.save(newSession);
        }
    }

    private List<ClassSessionDto.Creation> calculateSessionTimes(ClassSchedule schedule) {
        List<ClassSessionDto.Creation> sessions = new ArrayList<ClassSessionDto.Creation>();
        LocalDate startDate = schedule.getStartDate();
        int numWeeksAhead = 12;

        for (int i = 0; i < numWeeksAhead; i++) {
            LocalDate sessionDate = calculateNextSessionDate(startDate, schedule.getRecurrenceType(), i);
            if (schedule.getEndDate() != null && sessionDate.isAfter(schedule.getEndDate())) break;

            LocalDateTime startTime = sessionDate.atTime(schedule.getStartTime());
            LocalDateTime endTime = startTime.plus(schedule.getDuration());

            sessions.add(new ClassSessionDto.Creation(startTime, endTime));
        }
        return sessions;
    }

    private LocalDate calculateNextSessionDate(LocalDate startDate, RecurrenceType recurrenceType, int index) {
        if (recurrenceType == RecurrenceType.WEEKLY) return startDate.plusWeeks(index);
        else if (recurrenceType == RecurrenceType.FORTNIGHTLY) return startDate.plusWeeks(index * 2L);
        else return startDate.plusMonths(index);
    }
}
