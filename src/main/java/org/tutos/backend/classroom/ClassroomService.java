package org.tutos.backend.classroom;

import org.tutos.backend.calendar.ClassCalendarService;
import org.tutos.backend.calendar.dto.CalendarDtoMapper;
import org.tutos.backend.calendar.dto.ClassSessionDto;
import org.tutos.backend.classroom.dto.AnnouncementDto;
import org.tutos.backend.classroom.dto.AssignmentDto;
import org.tutos.backend.classroom.dto.ClassDto;
import org.tutos.backend.classroom.dto.ClassroomDtoMapper;
import org.tutos.backend.classroom.entity.*;
import org.tutos.backend.classroom.entity.*;
import org.tutos.backend.classroom.exception.*;
import org.tutos.backend.classroom.entity.Class;
import org.tutos.backend.classroom.exception.*;
import org.tutos.backend.classroom.exception.ClassNotFoundException;
import org.tutos.backend.common.exception.InvalidUserTypeException;
import org.tutos.backend.user.UserRepository;
import org.tutos.backend.user.dto.UserDto;
import org.tutos.backend.user.dto.UserDtoMapper;
import org.tutos.backend.user.entity.Student;
import org.tutos.backend.user.entity.Tutor;
import org.tutos.backend.user.entity.User;
import org.tutos.backend.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClassroomService {
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;
    private final AnnouncementRepository announcementRepository;
    private final AssignmentRepository assignmentRepository;

    private final ClassroomDtoMapper classroomDtoMapper;
    private final UserDtoMapper userDtoMapper;
    private final CalendarDtoMapper calendarDtoMapper;

    private final ClassCalendarService classCalendarService;

    public ClassroomService(
            ClassroomRepository classroomRepository,
            UserRepository userRepository,
            AnnouncementRepository announcementRepository,
            AssignmentRepository assignmentRepository,
            ClassroomDtoMapper classroomDtoMapper,
            UserDtoMapper userDtoMapper,
            CalendarDtoMapper calendarDtoMapper,
            ClassCalendarService classCalendarService
    ) {
        this.classroomRepository = classroomRepository;
        this.userRepository = userRepository;
        this.announcementRepository = announcementRepository;
        this.assignmentRepository = assignmentRepository;
        this.classroomDtoMapper = classroomDtoMapper;
        this.userDtoMapper = userDtoMapper;
        this.calendarDtoMapper = calendarDtoMapper;
        this.classCalendarService = classCalendarService;
    }

    @Transactional
    public ClassDto.Details createScheduledGroupClass(ClassDto.ScheduledGroupClassCreation dto) {
        Set<Long> tutorIds = (dto.tutorIds() == null) ? Set.of() : dto.tutorIds();
        Set<Long> studentIds = (dto.studentIds() == null) ? Set.of() : dto.studentIds();
        GroupClass groupClass = instantiateGroupClass(dto.title(), dto.description(), tutorIds, studentIds);

        classCalendarService.createClassSchedule(groupClass, dto.scheduleCreationDto());
        return classroomDtoMapper.toDetailsDto(classroomRepository.save(groupClass));
    }

    @Transactional
    public ClassDto.Details createShortTermGroupClass(ClassDto.ShortTermGroupClassCreation dto) {
        Set<Long> tutorIds = (dto.tutorIds() == null) ? Set.of() : dto.tutorIds();
        Set<Long> studentIds = (dto.studentIds() == null) ? Set.of() : dto.studentIds();
        GroupClass groupClass = instantiateGroupClass(dto.title(), dto.description(), tutorIds, studentIds);

        classCalendarService.generateShortTermSessions(groupClass, dto.sessionDates());
        return classroomDtoMapper.toDetailsDto(classroomRepository.save(groupClass));
    }

    @Transactional
    public ClassDto.Details createScheduledPrivateClass(ClassDto.ScheduledPrivateClassCreation dto) {
        Set<Long> tutorIds = (dto.tutorIds() == null) ? Set.of() : dto.tutorIds();
        PrivateClass privateClass = instantiatePrivateClass(dto.title(), dto.description(), tutorIds, dto.studentId());

        classCalendarService.createClassSchedule(privateClass, dto.scheduleCreationDto());
        return classroomDtoMapper.toDetailsDto(classroomRepository.save(privateClass));
    }

    @Transactional
    public ClassDto.Details createShortTermPrivateClass(ClassDto.ShortTermPrivateClassCreation dto) {
        Set<Long> tutorIds = (dto.tutorIds() == null) ? Set.of() : dto.tutorIds();
        PrivateClass privateClass = instantiatePrivateClass(dto.title(), dto.description(), tutorIds, dto.studentId());

        classCalendarService.generateShortTermSessions(privateClass, dto.sessionDates());
        return classroomDtoMapper.toDetailsDto(classroomRepository.save(privateClass));
    }

    public List<ClassDto.Details> getAllClasses() {
        return classroomRepository.findAll().stream()
                .map(classroomDtoMapper::toDetailsDto)
                .collect(Collectors.toList());
    }

    public ClassDto.Details getClassDetails(Long classId) {
        return classroomDtoMapper.toDetailsDto(classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId)));
    }

    @Transactional
    public ClassDto.Details updateClassDetails(Long classId, ClassDto.DetailsUpdate newClassDetails) {
        Class existingClass = classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId));

        existingClass.updateClassDetails(
                newClassDetails.title(),
                newClassDetails.description()
        );
        return classroomDtoMapper.toDetailsDto(classroomRepository.save(existingClass));
    }

    @Transactional
    public void deleteClass(Long classId) {
        Class existingClass = classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId));
        classroomRepository.delete(existingClass);
    }

    @Transactional
    public void addTutorToClass(Long classId, Long tutorId) {
        Class existingClass = classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId));
        User existingTutor = userRepository.findById(tutorId)
                .orElseThrow(() -> new UserNotFoundException(tutorId));

        if (!(existingTutor instanceof Tutor tutor)) {
            throw new InvalidUserTypeException("User " + tutorId + " is not a teacher");
        }

        if (tutorTeachesClass(existingClass, tutor)) {
            throw new UserAlreadyInClassException("User " + tutorId + " is already a teacher of class " + classId);
        }

        existingClass.addTutor(tutor);
        classroomRepository.save(existingClass);
    }

    public List<UserDto.Details> getTutorsInClass(Long classId) {
        Class existingClass = classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId));
        return existingClass.getTutors().stream().map(userDtoMapper::toDetailsDto).collect(Collectors.toList());
    }

    @Transactional
    public void removeTutorFromClass(Long classId, Long tutorId) {
        Class existingClass = classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId));

        User existingTutor = userRepository.findById(tutorId)
                .orElseThrow(() -> new UserNotFoundException(tutorId));

        if (!(existingTutor instanceof Tutor tutor)) {
            throw new InvalidUserTypeException("User " + tutorId + " is not a teacher");
        }

        if (!tutorTeachesClass(existingClass, tutor)) {
            throw new UserNotInClassException("User " + tutorId + " does not teach class " + classId);
        }

        existingClass.removeTutor(tutor);
    }

    @Transactional
    public void addStudentToClass(Long classId, Long studentId) {
        Class existingClass = classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId));
        User existingStudent = userRepository.findById(studentId)
                .orElseThrow(() -> new UserNotFoundException(studentId));

        if (!(existingStudent instanceof Student student)) {
            throw new InvalidUserTypeException("User " + studentId + " is not a student");
        }

        if (studentInClass(existingClass, student)) {
            throw new UserAlreadyInClassException("User " + studentId + " is already a student of class " + classId);
        }

        existingClass.addStudent(student);
        classroomRepository.save(existingClass);
    }

    public List<UserDto.Details> getStudentsInClass(Long classId) {
        Class existingClass = classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId));
        return existingClass.getStudents().stream().map(userDtoMapper::toDetailsDto).collect(Collectors.toList());
    }

    @Transactional
    public void removeStudentFromClass(Long classId, Long studentId) {
        Class existingClass = classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId));
        User existingStudent = userRepository.findById(studentId)
                .orElseThrow(() -> new UserNotFoundException(studentId));

        if (!(existingStudent instanceof Student student)) {
            throw new InvalidUserTypeException("User " + studentId + " is not a student");
        }

        if (!studentInClass(existingClass, student)) {
            throw new UserNotInClassException("User " + studentId + " does not attend class " + classId);
        }

        existingClass.removeStudent(student);
    }

    @Transactional
    public AssignmentDto.Details createAssignment(Long classId, AssignmentDto.Creation dto) {
        Class existingClass = classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId));

        Assignment assignment = new Assignment(
                existingClass,
                dto.title(),
                dto.description(),
                dto.dueDate()
        );

        existingClass.addAssignment(assignment);
        return classroomDtoMapper.toDetailsDto(assignmentRepository.save(assignment));
    }

    public List<AssignmentDto.Details> getAllClassAssignments(Long classId) {
        Class existingClass = classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId));

        return existingClass.getAssignments().stream()
                .map(classroomDtoMapper::toDetailsDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AssignmentDto.Details updateAssignmentDetails(
            Long classId,
            Long assignmentId,
            AssignmentDto.DetailsUpdate dto
    ) {
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException(assignmentId));

        if (assignmentNotInClass(classId, existingAssignment)) {
            throw new AssignmentNotInClassException(
                    "Assignment " + assignmentId + " does not belong to class " + classId
            );
        }

        existingAssignment.updateAssignmentDetails(
                dto.title(),
                dto.description(),
                dto.dueDate()
        );

        assignmentRepository.save(existingAssignment);
        return classroomDtoMapper.toDetailsDto(existingAssignment);
    }

    public AssignmentDto.Details getAssignmentDetails(Long classId, Long assignmentId) {
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException(assignmentId));

        if (assignmentNotInClass(classId, existingAssignment)) {
            throw new AssignmentNotInClassException(
                    "Assignment " + assignmentId + " does not belong to class " + classId
            );
        }

        return classroomDtoMapper.toDetailsDto(existingAssignment);
    }

    @Transactional
    public void deleteAssignment(Long classId, Long assignmentId) {
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException(assignmentId));
        Class existingClass = classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId));

        if (assignmentNotInClass(classId, existingAssignment)) {
            throw new AssignmentNotInClassException(
                    "Assignment " + assignmentId + " does not belong to class " + classId
            );
        }

        existingClass.removeAssignment(existingAssignment);
    }

    @Transactional
    public AnnouncementDto.Details createAnnouncement(Long classId, AnnouncementDto.Creation dto) {
        Class existingClass = classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId));

        Announcement announcement = new Announcement(
                existingClass,
                dto.title(),
                dto.message()
        );

        existingClass.addAnnouncement(announcement);
        return classroomDtoMapper.toDetailsDto(announcementRepository.save(announcement));
    }

    public List<AnnouncementDto.Details> getAllClassAnnouncements(Long classId) {
        Class existingClass = classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId));

        return existingClass.getAnnouncements().stream()
                .map(classroomDtoMapper::toDetailsDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AnnouncementDto.Details updateAnnouncementDetails(
            Long classId,
            Long announcementId,
            AnnouncementDto.DetailsUpdate dto
    ) {
        Announcement existingAnnouncement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new AnnouncementNotFoundException(announcementId));

        if (announcementNotInClass(classId, existingAnnouncement)) {
            throw new AnnouncementNotInClassException(
                    "Announcement " + announcementId + " does not belong to class " + classId
            );
        }

        existingAnnouncement.updateAnnouncementDetails(
                dto.title(),
                dto.message()
        );

        announcementRepository.save(existingAnnouncement);
        return classroomDtoMapper.toDetailsDto(existingAnnouncement);
    }

    public AnnouncementDto.Details getAnnouncementDetails(Long classId, Long announcementId) {
        Announcement existingAnnouncement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new AnnouncementNotFoundException(announcementId));

        if (announcementNotInClass(classId, existingAnnouncement)) {
            throw new AnnouncementNotInClassException(
                    "Announcement " + announcementId + " does not belong to class " + announcementId
            );
        }

        return classroomDtoMapper.toDetailsDto(existingAnnouncement);
    }

    @Transactional
    public void deleteAnnouncement(Long classId, Long announcementId) {
        Announcement existingAnnouncement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new AnnouncementNotFoundException(announcementId));
        Class existingClass = classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId));

        if (announcementNotInClass(classId, existingAnnouncement)) {
            throw new AnnouncementNotInClassException(
                    "Announcement " + announcementId + " does not belong to class " + announcementId
            );
        }

        existingClass.removeAnnouncement(existingAnnouncement);
    }

    public List<ClassSessionDto.Details> getClassSessions(
            Long classId,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        Class existingClass = classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId));

        return existingClass.getClassSessions().stream()
                .filter(s ->
                        (startDate == null || !s.getStartTime().isBefore(startDate))
                        && (endDate == null || !s.getEndTime().isAfter(endDate)))
                .map(calendarDtoMapper::toDetailsDto)
                .toList();
    }

    public boolean studentInClass(Class clazz, Student student) {
        return clazz.getStudents().contains(student);
    }

    public boolean tutorTeachesClass(Class clazz, Tutor tutor) {
        return clazz.getTutors().contains(tutor);
    }

    private GroupClass instantiateGroupClass(
            String title,
            String description,
            Set<Long> tutorIds,
            Set<Long> studentIds
    ) {
        List<User> tutors = userRepository.findAllById(tutorIds);
        validateUserIds(tutors, tutorIds);

        List<User> students = userRepository.findAllById(studentIds);
        validateUserIds(students, studentIds);

        Set<Tutor> tutorsTyped = new HashSet<Tutor>();
        for (User user : tutors) {
            if (!(user instanceof Tutor tutor)) {
                throw new InvalidUserTypeException("User " + user.getUserId() + " is not a tutor");
            }
            tutorsTyped.add(tutor);
        }

        Set<Student> studentsTyped = new HashSet<Student>();
        for (User user : students) {
            if (!(user instanceof Student student)) {
                throw new InvalidUserTypeException("User " + user.getUserId() + " is not a student");
            }
            studentsTyped.add(student);
        }

        return new GroupClass(title, description, tutorsTyped, studentsTyped);
    }

    private PrivateClass instantiatePrivateClass(
            String title,
            String description,
            Set<Long> tutorIds,
            Long studentId
    ) {
        List<User> tutors = userRepository.findAllById(tutorIds);
        validateUserIds(tutors, tutorIds);

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new UserNotFoundException(studentId));

        if (!(student instanceof Student s)) {
            throw new InvalidUserTypeException("User " + student.getUserId() + " is not a student");
        }

        Set<Tutor> tutorsTyped = new HashSet<Tutor>();
        for (User user : tutors) {
            if (!(user instanceof Tutor tutor)) {
                throw new InvalidUserTypeException("User " + user.getUserId() + " is not a tutor");
            }
            tutorsTyped.add(tutor);
        }

        return new PrivateClass(title, description, tutorsTyped, s);
    }

    private void validateUserIds(List<User> foundUsers, Set<Long> originalIds) {
        if (originalIds.isEmpty()) return;

        Set<Long> foundIds = foundUsers.stream().map(User::getUserId).collect(Collectors.toSet());
        Set<Long> missingIds = foundIds.stream().filter(i -> !originalIds.contains(i)).collect(Collectors.toSet());
        if (!missingIds.isEmpty()) {
            throw new UserNotFoundException("Users not found: " + missingIds);
        }
    }

    private boolean assignmentNotInClass(Long classId, Assignment assignment) {
        Class existingClass = classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId));
        return !existingClass.getAssignments().contains(assignment);
    }

    private boolean announcementNotInClass(Long classId, Announcement announcement) {
        Class existingClass = classroomRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId));
        return !existingClass.getAnnouncements().contains(announcement);
    }
}
