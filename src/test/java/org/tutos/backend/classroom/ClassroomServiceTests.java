//package org.lms.backend.classroom;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.lms.backend.classroom.dto.*;
//import org.lms.backend.classroom.entity.Announcement;
//import org.lms.backend.classroom.entity.Assignment;
//import org.lms.backend.classroom.entity.Class;
//import org.lms.backend.classroom.entity.GroupClass;
//import org.lms.backend.classroom.exception.*;
//import org.lms.backend.classroom.exception.ClassNotFoundException;
//import org.lms.backend.schedule.ClassScheduleRepository;
//import org.lms.backend.schedule.ClassScheduleService;
//import org.lms.backend.schedule.dto.ScheduleCreationDto;
//import org.lms.backend.schedule.dto.ScheduleDetailsDto;
//import org.lms.backend.schedule.entity.ClassSchedule;
//import org.lms.backend.schedule.entity.RecurrenceType;
//import org.lms.backend.user.UserRepository;
//import org.lms.backend.user.dto.UserDetailsDto;
//import org.lms.backend.user.dto.UserDtoMapper;
//import org.lms.backend.user.entity.Student;
//import org.lms.backend.user.entity.Tutor;
//import org.lms.backend.user.entity.User;
//import org.lms.backend.user.exception.UserNotFoundException;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.*;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ClassroomServiceTests {
//    @Mock
//    ClassroomRepository classroomRepository;
//    @Mock
//    UserRepository userRepository;
//    @Mock
//    AssignmentRepository assignmentRepository;
//    @Mock
//    AnnouncementRepository announcementRepository;
//    @Mock
//    UserDtoMapper userDtoMapper;
//    @Mock
//    ClassroomDtoMapper classroomDtoMapper;
//    @InjectMocks
//    ClassroomService classroomService;
//
//    @Mock
//    ClassScheduleRepository classScheduleRepository;
//    @InjectMocks
//    ClassScheduleService classScheduleService;
//
//    @Test
//    public void createScheduledGroupClass_classCreated() {
//        ScheduledGroupClassCreationDto classCreationDto = new ScheduledGroupClassCreationDto(
//                "groupClass1",
//                "group class",
//                new ScheduleCreationDto(
//                        DayOfWeek.MONDAY,
//                        LocalTime.of(15, 0, 0),
//                        60L,
//                        RecurrenceType.WEEKLY
//                ),
//                new HashSet<Long>(),
//                new HashSet<Long>()
//        );
//        when(classroomRepository.save(any(Class.class))).thenAnswer(i -> i.getArgument(0));
//        when(classScheduleRepository.save(any(ClassSchedule.class))).thenAnswer(i -> i.getArgument(0));
//        when(classroomDtoMapper.toDto(any(Class.class))).thenReturn(new ClassDetailsDto(
//                "groupClass1",
//                "group class",
//                new ScheduleDetailsDto(
//                        DayOfWeek.MONDAY,
//                        LocalTime.of(15, 0, 0),
//                        Duration.ofHours(1),
//                        RecurrenceType.WEEKLY
//                )
//        ));
//
//        ClassDetailsDto result = classroomService.createScheduledGroupClass(classCreationDto);
//        assertEquals(classCreationDto.title(), result.title());
//        assertEquals(classCreationDto.scheduleCreationDto().startTime(), result.scheduleDetailsDto().startTime());
//    }
//
//    @Test
//    public void createScheduledGroupClass_usersNotFound() {
//        Set<Long> ids = new HashSet<Long>();
//        ids.add(2L);
//        ids.add(3L);
//        List<User> users = new ArrayList<User>();
//        users.add(new Student());
//        ScheduledGroupClassCreationDto classCreationDto = new ScheduledGroupClassCreationDto(
//                "groupClass1",
//                "group class",
//                new ScheduleCreationDto(
//                        DayOfWeek.MONDAY,
//                        LocalTime.of(15, 0, 0),
//                        60L,
//                        RecurrenceType.WEEKLY
//                ),
//                new HashSet<Long>(),
//                ids
//        );
//        when(userRepository.findAllById(new HashSet<Long>())).thenReturn(new ArrayList<User>());
//        when(userRepository.findAllById(ids)).thenReturn(users);
//        assertThrows(UserNotFoundException.class, () -> classroomService.createScheduledGroupClass(classCreationDto));
//    }
//
//    @Test
//    public void createScheduledPrivateClass_classCreated() {
//        ScheduledPrivateClassCreationDto classCreationDto = new ScheduledPrivateClassCreationDto(
//                "privateClass1",
//                "private class",
//                new ScheduleCreationDto(
//                        DayOfWeek.MONDAY,
//                        LocalTime.of(15, 0, 0),
//                        60L,
//                        RecurrenceType.WEEKLY
//                ),
//                new HashSet<Long>(),
//                2L
//        );
//        when(userRepository.findById(2L)).thenReturn(Optional.of(new Student()));
//        when(classroomRepository.save(any(Class.class))).thenAnswer(i -> i.getArgument(0));
//        when(classroomDtoMapper.toDto(any(Class.class))).thenReturn(new ClassDetailsDto(
//                "privateClass1",
//                "private class",
//                new ScheduleDetailsDto(
//                        DayOfWeek.MONDAY,
//                        LocalTime.of(15, 0, 0),
//                        Duration.ofHours(1),
//                        RecurrenceType.WEEKLY
//                )
//        ));
//
//        ClassDetailsDto result = classroomService.createScheduledPrivateClass(classCreationDto);
//        assertEquals(classCreationDto.title(), result.title());
//        assertEquals(classCreationDto.scheduleCreationDto().startTime(), result.scheduleDetailsDto().startTime());
//    }
//
//    @Test
//    public void createScheduledPrivateClass_userNotFound() {
//        ScheduledPrivateClassCreationDto classCreationDto = new ScheduledPrivateClassCreationDto(
//                "privateClass1",
//                "private class",
//                new ScheduleCreationDto(
//                        DayOfWeek.MONDAY,
//                        LocalTime.of(15, 0, 0),
//                        60L,
//                        RecurrenceType.WEEKLY
//                ),
//                new HashSet<Long>(),
//                2L
//        );
//        when(userRepository.findById(2L)).thenReturn(Optional.empty());
//        assertThrows(UserNotFoundException.class, () -> classroomService.createScheduledPrivateClass(classCreationDto));
//    }
//
//    @Test
//    public void getAllClasses_classesReturned() {
//        List<Class> classes = new ArrayList<Class>();
//        classes.add(new GroupClass(
//                "groupClass1",
//                "group class",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        ));
//        classes.add(new GroupClass(
//                "groupClass2",
//                "group class",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        ));
//        classes.add(new GroupClass(
//                "groupClass3",
//                "group class",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        ));
//        when(classroomRepository.findAll()).thenReturn(classes);
//
//        List<ClassDetailsDto> result = classroomService.getAllClasses();
//        assertEquals(3, result.size());
//    }
//
//    @Test
//    public void getAllClasses_noClassesReturned() {
//        when(classroomRepository.findAll()).thenReturn(new ArrayList<Class>());
//
//        List<ClassDetailsDto> result = classroomService.getAllClasses();
//        assertEquals(0, result.size());
//    }
//
//    @Test
//    public void getClassDetails_classFound() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        ClassDetailsDto details = new ClassDetailsDto(
//                "title",
//                "desc",
//                new ScheduleDetailsDto(
//                        DayOfWeek.MONDAY,
//                        LocalTime.of(15, 0, 0),
//                        Duration.ofHours(1),
//                        RecurrenceType.WEEKLY
//                )
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(classroomDtoMapper.toDto(groupClass)).thenReturn(details);
//
//        ClassDetailsDto result = classroomService.getClassDetails(1L);
//        assertEquals("title", result.title());
//    }
//
//    @Test
//    public void getClassDetails_classNotFound() {
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.getClassDetails(1L));
//        verify(classroomRepository).findById(1L);
//    }
//
//    @Test
//    public void updateClassDetails_classUpdated() {
//        GroupClass existing = new GroupClass(
//                "old",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        ClassDetailsUpdateDto updated = new ClassDetailsUpdateDto(
//                "new",
//                "desc",
//                DayOfWeek.MONDAY,
//                LocalTime.NOON,
//                120L
//        );
//        ClassDetailsDto details = new ClassDetailsDto(
//                "new",
//                "desc",
//                new ScheduleDetailsDto(
//                        DayOfWeek.MONDAY,
//                        LocalTime.of(15, 0, 0),
//                        Duration.ofHours(1),
//                        RecurrenceType.WEEKLY
//                )
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(existing));
//        when(classroomRepository.save(any())).thenAnswer(i -> i.getArgument(0));
//        when(classroomDtoMapper.toDto(existing)).thenReturn(details);
//
//        ClassDetailsDto result = classroomService.updateClassDetails(1L, updated);
//        assertEquals("new", result.title());
//        verify(classroomRepository).save(existing);
//    }
//
//    @Test
//    public void updateClassDetails_classNotFound() {
//        ClassDetailsUpdateDto dto = new ClassDetailsUpdateDto(
//                "title",
//                "desc",
//                DayOfWeek.MONDAY,
//                LocalTime.NOON,
//                60L
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.updateClassDetails(1L, dto));
//        verify(classroomRepository).findById(1L);
//        verify(classroomRepository, never()).save(any());
//    }
//
//    @Test
//    public void deleteClass_classDeleted() {
//        Class groupClass = new GroupClass();
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//
//        classroomService.deleteClass(1L);
//        verify(classroomRepository).delete(groupClass);
//    }
//
//    @Test
//    public void deleteClass_classNotFound() {
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//        assertThrows(ClassNotFoundException.class, () -> classroomService.deleteClass(1L));
//    }
//
//    @Test
//    public void addTutorToClass_tutorAdded() {
//        GroupClass existing = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        User tutor = new Tutor();
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(existing));
//        when(userRepository.findById(2L)).thenReturn(Optional.of(tutor));
//        when(classroomRepository.save(any())).thenAnswer(i -> i.getArgument(0));
//
//        classroomService.addTutorToClass(1L, 2L);
//        verify(classroomRepository).save(existing);
//    }
//
//    @Test
//    public void addTutorToClass_classNotFound() {
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.addTutorToClass(1L, 2L));
//    }
//
//    @Test
//    public void addTutorToClass_userNotFound() {
//        GroupClass existing = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(existing));
//        when(userRepository.findById(2L)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () -> classroomService.addTutorToClass(1L, 2L));
//    }
//
//    @Test
//    public void getTutorsInClass_tutorsReturned() {
//        GroupClass existing = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        existing.addTutor(new Tutor());
//        existing.addTutor(new Tutor());
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        List<UserDetailsDto> result = classroomService.getTutorsInClass(1L);
//        assertEquals(2, result.size());
//    }
//
//    @Test
//    public void getTutorsInClass_noTutorsReturned() {
//        GroupClass existing = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        List<UserDetailsDto> result = classroomService.getTutorsInClass(1L);
//        assertEquals(0, result.size());
//    }
//
//    @Test
//    public void getTutorsInClass_classNotFound() {
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.getTutorsInClass(1L));
//    }
//
//    @Test
//    public void removeTutorFromClass_tutorRemoved() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        Tutor tutor = new Tutor(
//                "tutor1",
//                "yvette_dubuque@hotmail.com",
//                "tutorPass1",
//                "0400-000-005",
//                "Yvette",
//                "DuBuque",
//                LocalDate.of(1999, 11, 23)
//        );
//        groupClass.addTutor(tutor);
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(userRepository.findById(1L)).thenReturn(Optional.of(tutor));
//
//        classroomService.removeTutorFromClass(1L, 1L);
//        verify(classroomRepository).findById(1L);
//        verify(classroomRepository).findById(1L);
//    }
//
//    @Test
//    public void removeTutorFromClass_classNotFound() {
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.removeTutorFromClass(1L, 1L));
//    }
//
//    @Test
//    public void removeTutorFromClass_userNotFound() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () -> classroomService.removeTutorFromClass(1L, 1L));
//    }
//
//    @Test
//    public void removeTutorFromClass_tutorNotInClass() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        Tutor tutor = new Tutor(
//                "tutor1",
//                "yvette_dubuque@hotmail.com",
//                "tutorPass1",
//                "0400-000-005",
//                "Yvette",
//                "DuBuque",
//                LocalDate.of(1999, 11, 23)
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(userRepository.findById(1L)).thenReturn(Optional.of(tutor));
//
//        assertThrows(UserNotInClassException.class, () -> classroomService.removeTutorFromClass(1L, 1L));
//    }
//
//    @Test
//    public void addStudentToClass_studentAdded() {
//        GroupClass existing = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        User student = new Student();
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(existing));
//        when(userRepository.findById(2L)).thenReturn(Optional.of(student));
//        when(classroomRepository.save(any())).thenAnswer(i -> i.getArgument(0));
//
//        classroomService.addStudentToClass(1L, 2L);
//        verify(classroomRepository).save(existing);
//    }
//
//    @Test
//    public void addStudentToClass_classNotFound() {
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.addStudentToClass(1L, 2L));
//    }
//
//    @Test
//    public void addStudentToClass_userNotFound() {
//        GroupClass existing = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(existing));
//        when(userRepository.findById(2L)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () -> classroomService.addStudentToClass(1L, 2L));
//    }
//
//    @Test
//    public void getStudentsInClass_studentsReturned() {
//        GroupClass existing = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        existing.addStudent(new Student());
//        existing.addStudent(new Student());
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        List<UserDetailsDto> result = classroomService.getStudentsInClass(1L);
//        assertEquals(2, result.size());
//    }
//
//    @Test
//    public void getStudentsInClass_noStudentsReturned() {
//        GroupClass existing = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        List<UserDetailsDto> result = classroomService.getStudentsInClass(1L);
//        assertEquals(0, result.size());
//    }
//
//    @Test
//    public void getStudentsInClass_classNotFound() {
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.getStudentsInClass(1L));
//    }
//
//    @Test
//    public void removeStudentFromClass_studentRemoved() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        Student student = new Student(
//                "student1",
//                "yvette_dubuque@hotmail.com",
//                "studentPass1",
//                "0400-000-005",
//                "Yvette",
//                "DuBuque",
//                LocalDate.of(1999, 11, 23),
//                "Blakehurst High School",
//                10
//        );
//        groupClass.addStudent(student);
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(userRepository.findById(1L)).thenReturn(Optional.of(student));
//
//        classroomService.removeStudentFromClass(1L, 1L);
//        verify(classroomRepository).findById(1L);
//        verify(classroomRepository).findById(1L);
//    }
//
//    @Test
//    public void removeStudentFromClass_classNotFound() {
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.removeStudentFromClass(1L, 1L));
//    }
//
//    @Test
//    public void removeStudentFromClass_userNotFound() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () -> classroomService.removeStudentFromClass(1L, 1L));
//    }
//
//    @Test
//    public void removeStudentFromClass_studentNotInClass() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        Student student = new Student(
//                "student1",
//                "yvette_dubuque@hotmail.com",
//                "studentPass1",
//                "0400-000-005",
//                "Yvette",
//                "DuBuque",
//                LocalDate.of(1999, 11, 23),
//                "Blakehurst High School",
//                10
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(userRepository.findById(1L)).thenReturn(Optional.of(student));
//
//        assertThrows(UserNotInClassException.class, () -> classroomService.removeStudentFromClass(1L, 1L));
//    }
//
//    @Test
//    public void createAssignment_assignmentCreated() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        AssignmentCreationDto assignmentCreationDto = new AssignmentCreationDto(
//                "title",
//                "desc",
//                LocalDateTime.now()
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(assignmentRepository.save(any(Assignment.class))).thenAnswer(i -> i.getArgument(0));
//        when(classroomDtoMapper.toDto(any(Assignment.class))).thenReturn(new AssignmentDetailsDto(
//                "title",
//                "desc",
//                LocalDateTime.now(),
//                LocalDateTime.now()
//        ));
//
//        AssignmentDetailsDto result = classroomService.createAssignment(1L, assignmentCreationDto);
//        assertEquals(assignmentCreationDto.title(), result.title());
//        verify(assignmentRepository).save(any(Assignment.class));
//    }
//
//    @Test
//    public void createAssignment_classNotFound() {
//        AssignmentCreationDto assignmentCreationDto = new AssignmentCreationDto(
//                "title",
//                "desc",
//                LocalDateTime.now()
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.createAssignment(1L, assignmentCreationDto));
//    }
//
//    @Test
//    public void getAllClassAssignments_assignmentsReturned() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//
//        List<AssignmentDetailsDto> result = classroomService.getAllClassAssignments(1L);
//        assertEquals(0, result.size());
//    }
//
//    @Test
//    public void getAllClassAssignments_classNotFound() {
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.getAllClassAssignments(1L));
//    }
//
//    @Test
//    public void updateAssignmentDetails_assignmentUpdated() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        Assignment existing = new Assignment(
//                groupClass,
//                "title",
//                "desc",
//                LocalDateTime.now()
//        );
//        groupClass.addAssignment(existing);
//        AssignmentDetailsUpdateDto newDetails = new AssignmentDetailsUpdateDto(
//                "new",
//                "new",
//                LocalDateTime.now()
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(existing));
//        when(assignmentRepository.save(any(Assignment.class))).thenAnswer(i -> i.getArgument(0));
//        when(classroomDtoMapper.toDto(any(Assignment.class))).thenReturn(new AssignmentDetailsDto(
//                "new",
//                "new",
//                LocalDateTime.now(),
//                LocalDateTime.now()
//        ));
//
//        AssignmentDetailsDto result = classroomService.updateAssignmentDetails(1L, 1L, newDetails);
//        assertEquals(newDetails.title(), result.title());
//        verify(assignmentRepository).save(any(Assignment.class));
//    }
//
//    @Test
//    public void updateAssignmentDetails_classNotFound() {
//        Assignment existing = new Assignment(
//                new GroupClass(),
//                "title",
//                "desc",
//                LocalDateTime.now()
//        );
//        AssignmentDetailsUpdateDto newDetails = new AssignmentDetailsUpdateDto(
//                "new",
//                "new",
//                LocalDateTime.now()
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.updateAssignmentDetails(1L, 1L, newDetails));
//    }
//
//    @Test
//    public void updateAssignmentDetails_assignmentNotFound() {
//        AssignmentDetailsUpdateDto newDetails = new AssignmentDetailsUpdateDto(
//                "new",
//                "new",
//                LocalDateTime.now()
//        );
//        when(assignmentRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(AssignmentNotFoundException.class, () -> classroomService.updateAssignmentDetails(1L, 1L, newDetails));
//    }
//
//    @Test
//    public void updateAssignmentDetails_assignmentNotInClass() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        Assignment existing = new Assignment(
//                groupClass,
//                "title",
//                "desc",
//                LocalDateTime.now()
//        );
//        AssignmentDetailsUpdateDto newDetails = new AssignmentDetailsUpdateDto(
//                "new",
//                "new",
//                LocalDateTime.now()
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        assertThrows(AssignmentNotInClassException.class, () -> classroomService.updateAssignmentDetails(1L, 1L, newDetails));
//    }
//
//    @Test
//    public void getAssignmentDetails_assignmentReturned() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        Assignment existing = new Assignment(
//                groupClass,
//                "title",
//                "desc",
//                LocalDateTime.now()
//        );
//        groupClass.addAssignment(existing);
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(existing));
//        when(classroomDtoMapper.toDto(any(Assignment.class))).thenReturn(new AssignmentDetailsDto(
//                "title",
//                "desc",
//                LocalDateTime.now(),
//                LocalDateTime.now()
//        ));
//
//        AssignmentDetailsDto result = classroomService.getAssignmentDetails(1L, 1L);
//        assertEquals(existing.getTitle(), result.title());
//        assertEquals(existing.getDescription(), result.description());
//    }
//
//    @Test
//    public void getAssignmentDetails_classNotFound() {
//        Assignment existing = new Assignment(
//                new GroupClass(),
//                "title",
//                "desc",
//                LocalDateTime.now()
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.getAssignmentDetails(1L, 1L));
//    }
//
//    @Test
//    public void getAssignmentDetails_assignmentNotFound() {
//        when(assignmentRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(AssignmentNotFoundException.class, () -> classroomService.getAssignmentDetails(1L, 1L));
//    }
//
//    @Test
//    public void getAssignmentDetails_assignmentNotInClass() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        Assignment existing = new Assignment(
//                groupClass,
//                "title",
//                "desc",
//                LocalDateTime.now()
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        assertThrows(AssignmentNotInClassException.class, () -> classroomService.getAssignmentDetails(1L, 1L));
//    }
//
//    @Test
//    public void deleteAssignment_assignmentDeleted() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        Assignment existing = new Assignment(
//                groupClass,
//                "title",
//                "desc",
//                LocalDateTime.now()
//        );
//        groupClass.addAssignment(existing);
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        classroomService.deleteAssignment(1L, 1L);
//        assertFalse(groupClass.getAssignments().contains(existing));
//    }
//
//    @Test
//    public void deleteAssignment_classNotFound() {
//        Assignment existing = new Assignment(
//                new GroupClass(),
//                "title",
//                "desc",
//                LocalDateTime.now()
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.deleteAssignment(1L, 1L));
//    }
//
//    @Test
//    public void deleteAssignment_assignmentNotFound() {
//        when(assignmentRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(AssignmentNotFoundException.class, () -> classroomService.deleteAssignment(1L, 1L));
//    }
//
//    @Test
//    public void deleteAssignment_assignmentNotInClass() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        Assignment existing = new Assignment(
//                groupClass,
//                "title",
//                "desc",
//                LocalDateTime.now()
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        assertThrows(AssignmentNotInClassException.class, () -> classroomService.deleteAssignment(1L, 1L));
//    }
//
//    @Test
//    public void createAnnouncement_announcementCreated() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        AnnouncementCreationDto announcementCreationDto = new AnnouncementCreationDto(
//                "title",
//                "desc"
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(announcementRepository.save(any(Announcement.class))).thenAnswer(i -> i.getArgument(0));
//        when(classroomDtoMapper.toDto(any(Announcement.class))).thenReturn(new AnnouncementDetailsDto(
//                "title",
//                "desc",
//                LocalDateTime.now()
//        ));
//
//        AnnouncementDetailsDto result = classroomService.createAnnouncement(1L, announcementCreationDto);
//        assertEquals(announcementCreationDto.title(), result.title());
//        verify(announcementRepository).save(any(Announcement.class));
//    }
//
//    @Test
//    public void createAnnouncement_classNotFound() {
//        AnnouncementCreationDto announcementCreationDto = new AnnouncementCreationDto(
//                "title",
//                "desc"
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.createAnnouncement(1L, announcementCreationDto));
//    }
//
//    @Test
//    public void getAllClassAnnouncements_announcementsReturned() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//
//        List<AnnouncementDetailsDto> result = classroomService.getAllClassAnnouncements(1L);
//        assertEquals(0, result.size());
//    }
//
//    @Test
//    public void getAllClassAnnouncements_classNotFound() {
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.getAllClassAnnouncements(1L));
//    }
//
//    @Test
//    public void updateAnnouncementDetails_announcementUpdated() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        Announcement existing = new Announcement(
//                groupClass,
//                "title",
//                "desc"
//        );
//        groupClass.addAnnouncement(existing);
//        AnnouncementDetailsUpdateDto newDetails = new AnnouncementDetailsUpdateDto(
//                "new",
//                "new"
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(announcementRepository.findById(1L)).thenReturn(Optional.of(existing));
//        when(announcementRepository.save(any(Announcement.class))).thenAnswer(i -> i.getArgument(0));
//        when(classroomDtoMapper.toDto(any(Announcement.class))).thenReturn(new AnnouncementDetailsDto(
//                "new",
//                "new",
//                LocalDateTime.now()
//        ));
//
//        AnnouncementDetailsDto result = classroomService.updateAnnouncementDetails(1L, 1L, newDetails);
//        assertEquals(newDetails.title(), result.title());
//        verify(announcementRepository).save(any(Announcement.class));
//    }
//
//    @Test
//    public void updateAnnouncementDetails_classNotFound() {
//        Announcement existing = new Announcement(
//                new GroupClass(),
//                "title",
//                "desc"
//        );
//        AnnouncementDetailsUpdateDto newDetails = new AnnouncementDetailsUpdateDto(
//                "new",
//                "new"
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//        when(announcementRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.updateAnnouncementDetails(1L, 1L, newDetails));
//    }
//
//    @Test
//    public void updateAnnouncementDetails_announcementNotFound() {
//        AnnouncementDetailsUpdateDto newDetails = new AnnouncementDetailsUpdateDto(
//                "new",
//                "new"
//        );
//        when(announcementRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(AnnouncementNotFoundException.class, () -> classroomService.updateAnnouncementDetails(1L, 1L, newDetails));
//    }
//
//    @Test
//    public void updateAnnouncementDetails_announcementNotInClass() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        Announcement existing = new Announcement(
//                groupClass,
//                "title",
//                "desc"
//        );
//        AnnouncementDetailsUpdateDto newDetails = new AnnouncementDetailsUpdateDto(
//                "new",
//                "new"
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(announcementRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        assertThrows(AnnouncementNotInClassException.class, () -> classroomService.updateAnnouncementDetails(1L, 1L, newDetails));
//    }
//
//    @Test
//    public void getAnnouncementDetails_announcementReturned() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        Announcement existing = new Announcement(
//                groupClass,
//                "title",
//                "desc"
//        );
//        groupClass.addAnnouncement(existing);
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(announcementRepository.findById(1L)).thenReturn(Optional.of(existing));
//        when(classroomDtoMapper.toDto(any(Announcement.class))).thenReturn(new AnnouncementDetailsDto(
//                "title",
//                "desc",
//                LocalDateTime.now()
//        ));
//
//        AnnouncementDetailsDto result = classroomService.getAnnouncementDetails(1L, 1L);
//        assertEquals(existing.getTitle(), result.title());
//        assertEquals(existing.getMessage(), result.message());
//    }
//
//    @Test
//    public void getAnnouncementDetails_classNotFound() {
//        Announcement existing = new Announcement(
//                new GroupClass(),
//                "title",
//                "desc"
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//        when(announcementRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.getAnnouncementDetails(1L, 1L));
//    }
//
//    @Test
//    public void getAnnouncementDetails_announcementNotFound() {
//        when(announcementRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(AnnouncementNotFoundException.class, () -> classroomService.getAnnouncementDetails(1L, 1L));
//    }
//
//    @Test
//    public void getAnnouncementDetails_announcementNotInClass() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        Announcement existing = new Announcement(
//                groupClass,
//                "title",
//                "desc"
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(announcementRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        assertThrows(AnnouncementNotInClassException.class, () -> classroomService.getAnnouncementDetails(1L, 1L));
//    }
//
//    @Test
//    public void deleteAnnouncement_announcementDeleted() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        Announcement existing = new Announcement(
//                groupClass,
//                "title",
//                "desc"
//        );
//        groupClass.addAnnouncement(existing);
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(announcementRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        classroomService.deleteAnnouncement(1L, 1L);
//        assertFalse(groupClass.getAnnouncements().contains(existing));
//    }
//
//    @Test
//    public void deleteAnnouncement_classNotFound() {
//        Announcement existing = new Announcement(
//                new GroupClass(),
//                "title",
//                "desc"
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
//        when(announcementRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        assertThrows(ClassNotFoundException.class, () -> classroomService.deleteAnnouncement(1L, 1L));
//    }
//
//    @Test
//    public void deleteAnnouncement_announcementNotFound() {
//        when(announcementRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(AnnouncementNotFoundException.class, () -> classroomService.deleteAnnouncement(1L, 1L));
//    }
//
//    @Test
//    public void deleteAnnouncement_announcementNotInClass() {
//        GroupClass groupClass = new GroupClass(
//                "title",
//                "desc",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        Announcement existing = new Announcement(
//                groupClass,
//                "title",
//                "desc"
//        );
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(groupClass));
//        when(announcementRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        assertThrows(AnnouncementNotInClassException.class, () -> classroomService.deleteAnnouncement(1L, 1L));
//    }
//}
