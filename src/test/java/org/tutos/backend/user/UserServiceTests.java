//package org.lms.backend.user;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.lms.backend.classroom.dto.AssignmentDetailsDto;
//import org.lms.backend.classroom.dto.ClassDetailsDto;
//import org.lms.backend.classroom.dto.ClassroomDtoMapper;
//import org.lms.backend.classroom.entity.Assignment;
//import org.lms.backend.classroom.entity.Class;
//import org.lms.backend.classroom.entity.GroupClass;
//import org.lms.backend.classroom.entity.PrivateClass;
//import org.lms.backend.common.exception.InvalidUserTypeException;
//import org.lms.backend.common.exception.ValidationException;
//import org.lms.backend.schedule.dto.ScheduleDetailsDto;
//import org.lms.backend.schedule.entity.RecurrenceType;
//import org.lms.backend.user.dto.UserDetailsDto;
//import org.lms.backend.user.dto.UserDetailsUpdateDto;
//import org.lms.backend.user.dto.UserDtoMapper;
//import org.lms.backend.user.entity.*;
//import org.lms.backend.user.exception.UserNotFoundException;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.*;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceTests {
//    @Mock
//    UserRepository userRepository;
//    @Mock
//    UserDtoMapper userDtoMapper;
//    @Mock
//    ClassroomDtoMapper classroomDtoMapper;
//    @InjectMocks
//    UserService userService;
//
//    @Test
//    public void getAllUsers_usersReturned() {
//        List<User> users = new ArrayList<User>();
//        Student student = new Student();
//        Tutor tutor = new Tutor();
//        Parent parent = new Parent();
//        Admin admin = new Admin();
//        Master master = new Master();
//        users.add(student);
//        users.add(tutor);
//        users.add(parent);
//        users.add(admin);
//        users.add(master);
//        when(userRepository.findAll()).thenReturn(users);
//
//        List<UserDetailsDto> result = userService.getAllUsers();
//        assertEquals(5, result.size());
//    }
//
//    @Test
//    public void getAllUsers_noUsersReturned() {
//        when(userRepository.findAll()).thenReturn(new ArrayList<User>());
//        List<UserDetailsDto> result = userService.getAllUsers();
//        assertEquals(0, result.size());
//    }
//
//    @Test
//    public void getUserDetails_userReturned() {
//        UserDetailsDto userDetailsDto = new UserDetailsDto(
//                "yvette_dubuque@hotmail.com",
//                "0400-000-005",
//                "Yvette",
//                "DuBuque",
//                LocalDate.of(1999, 11, 23)
//        );
//        User student = new Student(
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
//        when(userRepository.findById(1L)).thenReturn(Optional.of(student));
//        when(userDtoMapper.toDto(student)).thenReturn(userDetailsDto);
//
//        UserDetailsDto result = userService.getUserDetails(1L);
//        assertEquals(userDetailsDto.email(), result.email());
//        assertEquals(userDetailsDto.nameFirst(), result.nameFirst());
//    }
//
//    @Test
//    public void getUserDetails_userNotFound() {
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () -> userService.getUserDetails(1L));
//    }
//
//    @Test
//    public void updateUserDetails_detailsUpdated() {
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
//        UserDetailsUpdateDto newDetails = new UserDetailsUpdateDto(
//                "newemail@hotmail.com",
//                "0400-000-005",
//                "Yvette2",
//                "DuBuque2",
//                LocalDate.of(1999, 11, 23)
//        );
//        UserDetailsDto userDetailsDto = new UserDetailsDto(
//                "newemail@hotmail.com",
//                "0400-000-005",
//                "Yvette2",
//                "DuBuque2",
//                LocalDate.of(1999, 11, 23)
//        );
//        when(userRepository.findById(1L)).thenReturn(Optional.of(student));
//        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
//        when(userDtoMapper.toDto(student)).thenReturn(userDetailsDto);
//
//        UserDetailsDto result = userService.updateUserDetails(1L, newDetails);
//        verify(userRepository).findById(1L);
//        verify(userRepository).save(student);
//        assertEquals(newDetails.email(), result.email());
//        assertEquals(newDetails.nameFirst(), result.nameFirst());
//        assertEquals(newDetails.nameLast(), result.nameLast());
//    }
//
//    @Test
//    public void updateUserDetails_userNotFound() {
//        UserDetailsUpdateDto newDetails = new UserDetailsUpdateDto(
//                "newemail@hotmail.com",
//                "0400-000-005",
//                "Yvette2",
//                "DuBuque2",
//                LocalDate.of(1999, 11, 23)
//        );
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () -> userService.updateUserDetails(1L, newDetails));
//    }
//
//    @Test
//    public void updateUserDetails_invalidDetails() {
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
//        UserDetailsUpdateDto newDetails = new UserDetailsUpdateDto(
//                "newemail@hotmail.com",
//                "0400-000-005",
//                "Yvette2",
//                "DuBuque2",
//                LocalDate.of(1999, 11, 23)
//        );
//        when(userRepository.findById(1L)).thenReturn(Optional.of(student));
//        when(userRepository.existsByEmail(newDetails.email())).thenReturn(true);
//
//        assertThrows(ValidationException.class, () ->
//                userService.updateUserDetails(1L, newDetails)
//        );
//        verify(userRepository, never()).save(any(User.class));
//    }
//
//    @Test
//    public void getUserLessons_lessonsReturned() {
//        Set<Class> lessons = new HashSet<Class>();
//        PrivateClass privateClass = new PrivateClass(
//                "privateClass1",
//                "private class",
//                new HashSet<Tutor>(),
//                new Student()
//        );
//        GroupClass groupClass = new GroupClass(
//                "groupClass1",
//                "group class",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        lessons.add(privateClass);
//        lessons.add(groupClass);
//
//        Student student = new Student();
//        student.setLessons(lessons);
//
//        groupClass.addStudent(student);
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(student));
//
//        List<ClassDetailsDto> result = userService.getUserLessons(1L);
//        assertEquals(2, result.size());
//    }
//
//    @Test
//    public void getUserLessons_noLessonsReturned() {
//        Set<Class> lessons = new HashSet<Class>();
//
//        Student student = new Student();
//        student.setLessons(lessons);
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(student));
//
//        List<ClassDetailsDto> result = userService.getUserLessons(1L);
//        assertEquals(0, result.size());
//    }
//
//    @Test
//    public void getUserLessons_userNotFound() {
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//        assertThrows(UserNotFoundException.class, () -> userService.getUserLessons(1L));
//    }
//
//    @Test
//    public void getUserLessons_userNotLearner() {
//        Admin admin = new Admin();
//        when(userRepository.findById(1L)).thenReturn(Optional.of(admin));
//        assertThrows(InvalidUserTypeException.class, () -> userService.getUserLessons(1L));
//    }
//
//    @Test
//    public void getUserClasses_classesReturned() {
//        Set<Class> classes = new HashSet<Class>();
//        PrivateClass privateClass = new PrivateClass(
//                "privateClass1",
//                "private class",
//                new HashSet<Tutor>(),
//                new Student()
//        );
//        GroupClass groupClass = new GroupClass(
//                "groupClass1",
//                "group class",
//                new HashSet<Tutor>(),
//                new HashSet<Student>()
//        );
//        classes.add(privateClass);
//        classes.add(groupClass);
//
//        Tutor tutor = new Tutor();
//        tutor.setClasses(classes);
//
//        privateClass.addTutor(tutor);
//        groupClass.addTutor(tutor);
//        groupClass.addStudent(new Student());
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(tutor));
//        when(classroomDtoMapper.toDto(privateClass)).thenReturn(new ClassDetailsDto(
//                "privateClass1",
//                "private class",
//                new ScheduleDetailsDto(
//                        DayOfWeek.MONDAY,
//                        LocalTime.of(15, 0, 0),
//                        Duration.ofHours(1),
//                        RecurrenceType.WEEKLY
//                )
//        ));
//        when(classroomDtoMapper.toDto(groupClass)).thenReturn(new ClassDetailsDto(
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
//        List<ClassDetailsDto> result = userService.getUserClasses(1L);
//        assertEquals(2, result.size());
//    }
//
//    @Test
//    public void getUserClasses_noClassesReturned() {
//        Set<Class> classes = new HashSet<Class>();
//
//        Tutor tutor = new Tutor();
//        tutor.setClasses(classes);
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(tutor));
//
//        List<ClassDetailsDto> result = userService.getUserClasses(1L);
//        assertEquals(0, result.size());
//    }
//
//    @Test
//    public void getUserClasses_userNotFound() {
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//        assertThrows(UserNotFoundException.class, () -> userService.getUserClasses(1L));
//    }
//
//    @Test
//    public void getUserClasses_userNotTeacher() {
//        Admin admin = new Admin();
//        when(userRepository.findById(1L)).thenReturn(Optional.of(admin));
//        assertThrows(InvalidUserTypeException.class, () -> userService.getUserClasses(1L));
//    }
//
//    @Test
//    public void getUserAssignments_assignmentsReturned() {
//        List<Assignment> assignments = new ArrayList<Assignment>();
//        Assignment ass1 = new Assignment(
//                new GroupClass(),
//                "assignment",
//                "description",
//                LocalDateTime.now()
//        );
//        Assignment ass2 = new Assignment(
//                new GroupClass(),
//                "assignment",
//                "description",
//                LocalDateTime.now()
//        );
//        assignments.add(ass1);
//        assignments.add(ass2);
//
//        Student student = new Student();
//        student.setAssignments(assignments);
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(student));
//        when(classroomDtoMapper.toDto(ass1)).thenReturn(new AssignmentDetailsDto(
//                "assignment",
//                "description",
//                LocalDateTime.now(),
//                LocalDateTime.now()
//        ));
//        when(classroomDtoMapper.toDto(ass2)).thenReturn(new AssignmentDetailsDto(
//                "assignment",
//                "description",
//                LocalDateTime.now(),
//                LocalDateTime.now()
//        ));
//
//        List<AssignmentDetailsDto> result = userService.getUserAssignments(1L);
//        assertEquals(2, result.size());
//    }
//
//    @Test
//    public void getUserAssignments_noAssignmentsReturned() {
//        List<Assignment> assignments = new ArrayList<Assignment>();
//
//        Student student = new Student();
//        student.setAssignments(assignments);
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(student));
//
//        List<AssignmentDetailsDto> result = userService.getUserAssignments(1L);
//        assertEquals(0, result.size());
//    }
//
//    @Test
//    public void getUserAssignments_userNotFound() {
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//        assertThrows(UserNotFoundException.class, () -> userService.getUserAssignments(1L));
//    }
//
//    @Test
//    public void getUserAssignments_userNotLearner() {
//        Admin admin = new Admin();
//        when(userRepository.findById(1L)).thenReturn(Optional.of(admin));
//        assertThrows(InvalidUserTypeException.class, () -> userService.getUserAssignments(1L));
//    }
//
//    @Test
//    public void deleteUser_userDeleted() {
//        User user = new Student();
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//        userService.deleteUser(1L);
//        verify(userRepository).delete(user);
//    }
//
//    @Test
//    public void deleteUser_userNotFound() {
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
//    }
//}
