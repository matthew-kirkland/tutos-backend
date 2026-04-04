//package org.lms.backend.classroom;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.lms.backend.auth.RoleRepository;
//import org.lms.backend.classroom.dto.*;
//import org.lms.backend.schedule.dto.ScheduleCreationDto;
//import org.lms.backend.schedule.dto.ScheduleDetailsDto;
//import org.lms.backend.schedule.entity.RecurrenceType;
//import org.lms.backend.user.UserRepository;
//import org.lms.backend.user.dto.UserDetailsDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.time.DayOfWeek;
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.HashSet;
//
//import static org.hamcrest.Matchers.is;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(ClassroomController.class)
//@WithMockUser(username = "testuser", roles = {"MASTER"})
//public class ClassroomControllerTests {
//    @Autowired
//    MockMvc mvc;
//    @Autowired
//    ObjectMapper objectMapper;
//    @MockitoBean
//    ClassroomService classroomService;
//    @MockitoBean
//    ClassroomRepository classroomRepository;
//    @MockitoBean
//    UserRepository userRepository;
//    @MockitoBean
//    RoleRepository roleRepository;
//
//    @Test
//    public void createScheduledGroupClass_classCreated() throws Exception {
//        ScheduledGroupClassCreationDto scheduledGroupClassCreationDto = new ScheduledGroupClassCreationDto(
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
//        when(classroomService.createScheduledGroupClass(any(ScheduledGroupClassCreationDto.class))).thenReturn(
//                new ClassDetailsDto(
//                        "groupClass1",
//                        "group class",
//                        new ScheduleDetailsDto(
//                                DayOfWeek.MONDAY,
//                                LocalTime.of(15, 0, 0),
//                                Duration.ofHours(1),
//                                RecurrenceType.WEEKLY
//                        )
//                )
//        );
//        mvc.perform(post("/classes/new/groupclass")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(scheduledGroupClassCreationDto)))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    public void createScheduledGroupClass_invalidDetails() throws Exception {
//        ScheduledGroupClassCreationDto scheduledGroupClassCreationDto = new ScheduledGroupClassCreationDto(
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
//        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(classroomService).createScheduledGroupClass(any(ScheduledGroupClassCreationDto.class));
//        mvc.perform(post("/classes/new/groupclass")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(scheduledGroupClassCreationDto)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void createScheduledPrivateClass_classCreated() throws Exception {
//        ScheduledPrivateClassCreationDto scheduledPrivateClassCreationDto = new ScheduledPrivateClassCreationDto(
//                "privateClass1",
//                "private class",
//                new ScheduleCreationDto(
//                        DayOfWeek.MONDAY,
//                        LocalTime.of(15, 0, 0),
//                        60L,
//                        RecurrenceType.WEEKLY
//                ),
//                new HashSet<Long>(),
//                1L
//        );
//        when(classroomService.createScheduledPrivateClass(any(ScheduledPrivateClassCreationDto.class))).thenReturn(new ClassDetailsDto(
//                "groupClass1",
//                "group class",
//                new ScheduleDetailsDto(
//                        DayOfWeek.MONDAY,
//                        LocalTime.of(15, 0, 0),
//                        Duration.ofHours(1),
//                        RecurrenceType.WEEKLY
//                )
//        ));
//        mvc.perform(post("/classes/new/privateclass")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(scheduledPrivateClassCreationDto)))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    public void createScheduledPrivateClass_invalidDetails() throws Exception {
//        ScheduledPrivateClassCreationDto scheduledPrivateClassCreationDto = new ScheduledPrivateClassCreationDto(
//                "privateClass1",
//                "private class",
//                new ScheduleCreationDto(
//                        DayOfWeek.MONDAY,
//                        LocalTime.of(15, 0, 0),
//                        60L,
//                        RecurrenceType.WEEKLY
//                ),
//                new HashSet<Long>(),
//                1L
//        );
//        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(classroomService).createScheduledPrivateClass(any(ScheduledPrivateClassCreationDto.class));
//        mvc.perform(post("/classes/new/privateclass")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(scheduledPrivateClassCreationDto)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void getAllClasses_classesReturned() throws Exception {
//        when(classroomService.getAllClasses()).thenReturn(new ArrayList<ClassDetailsDto>());
//        mvc.perform(get("/classes"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void getClassDetails_returnsClass() throws Exception {
//        when(classroomService.getClassDetails(1L)).thenReturn(new ClassDetailsDto(
//                "groupClass1",
//                "group class",
//                new ScheduleDetailsDto(
//                        DayOfWeek.MONDAY,
//                        LocalTime.of(15, 0, 0),
//                        Duration.ofHours(1),
//                        RecurrenceType.WEEKLY
//                )
//        ));
//        mvc.perform(get("/classes/1"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void getClassDetails_invalidClassId() throws Exception {
//        when(classroomService.getClassDetails(1L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
//        mvc.perform(get("/classes/1"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void updateClassDetails_detailsUpdated() throws Exception {
//        ClassDetailsUpdateDto newDetails = new ClassDetailsUpdateDto(
//                "groupClass1",
//                "group class",
//                DayOfWeek.MONDAY,
//                LocalTime.of(15, 0, 0),
//                60L
//        );
//        when(classroomService.updateClassDetails(eq(1L), any(ClassDetailsUpdateDto.class))).thenReturn(new ClassDetailsDto(
//                "groupClass1",
//                "group class",
//                new ScheduleDetailsDto(
//                        DayOfWeek.MONDAY,
//                        LocalTime.of(15, 0, 0),
//                        Duration.ofHours(1),
//                        RecurrenceType.WEEKLY
//                )
//        ));
//        mvc.perform(put("/classes/1")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newDetails)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void updateClassDetails_invalidClassId() throws Exception {
//        ClassDetailsUpdateDto newDetails = new ClassDetailsUpdateDto(
//                "groupClass1",
//                "group class",
//                DayOfWeek.MONDAY,
//                LocalTime.of(15, 0, 0),
//                60L
//        );
//        when(classroomService.updateClassDetails(eq(1L), any(ClassDetailsUpdateDto.class))).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
//        mvc.perform(put("/classes/1")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newDetails)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void updateClassDetails_invalidNewDetails() throws Exception {
//        ClassDetailsUpdateDto newDetails = new ClassDetailsUpdateDto(
//                "groupClass1",
//                "group class",
//                DayOfWeek.MONDAY,
//                LocalTime.of(15, 0, 0),
//                60L
//        );
//        when(classroomService.updateClassDetails(eq(1L), any(ClassDetailsUpdateDto.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
//        mvc.perform(put("/classes/1")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newDetails)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void deleteClass_classDeleted() throws Exception {
//        doNothing().when(classroomService).deleteClass(1L);
//        mvc.perform(delete("/classes/1")
//                        .with(csrf()))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void deleteClass_invalidClassId() throws Exception {
//        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(classroomService).deleteClass(1L);
//        mvc.perform(delete("/classes/1")
//                        .with(csrf()))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void addTutorToClass_tutorAdded() throws Exception {
//        doNothing().when(classroomService).addTutorToClass(1L, 2L);
//        mvc.perform(post("/classes/1/tutors")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(2L)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void addTutorToClass_invalidClassId() throws Exception {
//        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(classroomService).addTutorToClass(1L, 2L);
//        mvc.perform(post("/classes/1/tutors")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(2L)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void addTutorToClass_invalidTutorId() throws Exception {
//        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(classroomService).addTutorToClass(1L, 2L);
//        mvc.perform(post("/classes/1/tutors")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(2L)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void getClassTutors_tutorsReturned() throws Exception {
//        when(classroomService.getTutorsInClass(1L)).thenReturn(new ArrayList<UserDetailsDto>());
//        mvc.perform(get("/classes/1/tutors")
//                        .with(csrf()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()", is(0)));
//    }
//
//    @Test
//    public void getClassTutors_invalidClassId() throws Exception {
//        when(classroomService.getTutorsInClass(1L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
//        mvc.perform(get("/classes/1/tutors")
//                        .with(csrf()))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void removeTutorFromClass_tutorRemoved() throws Exception {
//        doNothing().when(classroomService).removeTutorFromClass(1L, 2L);
//        mvc.perform(delete("/classes/1/tutors")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(2L)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void removeTutorFromClass_invalidClassId() throws Exception {
//        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(classroomService).removeTutorFromClass(1L, 2L);
//        mvc.perform(delete("/classes/1/tutors")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(2L)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void removeTutorFromClass_invalidTutorId() throws Exception {
//        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(classroomService).removeTutorFromClass(1L, 2L);
//        mvc.perform(delete("/classes/1/tutors")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(2L)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void addStudentToClass_studentAdded() throws Exception {
//        doNothing().when(classroomService).addStudentToClass(1L, 2L);
//        mvc.perform(post("/classes/1/students")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(2L)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void addStudentToClass_invalidClassId() throws Exception {
//        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(classroomService).addStudentToClass(1L, 2L);
//        mvc.perform(post("/classes/1/students")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(2L)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void addStudentToClass_invalidStudentId() throws Exception {
//        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(classroomService).addStudentToClass(1L, 2L);
//        mvc.perform(post("/classes/1/students")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(2L)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void getClassStudents_studentsReturned() throws Exception {
//        when(classroomService.getStudentsInClass(1L)).thenReturn(new ArrayList<UserDetailsDto>());
//        mvc.perform(get("/classes/1/students")
//                        .with(csrf()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()", is(0)));
//    }
//
//    @Test
//    public void getClassStudents_invalidClassId() throws Exception {
//        when(classroomService.getStudentsInClass(1L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
//        mvc.perform(get("/classes/1/students")
//                        .with(csrf()))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void removeStudentFromClass_studentRemoved() throws Exception {
//        doNothing().when(classroomService).removeStudentFromClass(1L, 2L);
//        mvc.perform(delete("/classes/1/students")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(2L)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void removeStudentFromClass_invalidClassId() throws Exception {
//        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(classroomService).removeStudentFromClass(1L, 2L);
//        mvc.perform(delete("/classes/1/students")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(2L)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void removeStudentFromClass_invalidStudentId() throws Exception {
//        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(classroomService).removeStudentFromClass(1L, 2L);
//        mvc.perform(delete("/classes/1/students")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(2L)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void createAssignment_assignmentCreated() throws Exception {
//        AssignmentCreationDto assignmentCreationDto = new AssignmentCreationDto(
//                "assignment",
//                "description",
//                LocalDateTime.now().plusDays(7)
//        );
//        when(classroomService.createAssignment(eq(1L), any(AssignmentCreationDto.class))).thenReturn(new AssignmentDetailsDto(
//                "assignment",
//                "description",
//                LocalDateTime.now(),
//                LocalDateTime.now()
//        ));
//        mvc.perform(post("/classes/1/assignments")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(assignmentCreationDto)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void createAssignment_invalidClassId() throws Exception {
//        AssignmentCreationDto assignmentCreationDto = new AssignmentCreationDto(
//                "assignment",
//                "description",
//                LocalDateTime.now().plusDays(7)
//        );
//        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(classroomService).createAssignment(eq(1L), any(AssignmentCreationDto.class));
//        mvc.perform(post("/classes/1/assignments")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(assignmentCreationDto)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void createAssignment_invalidAssignmentDetails() throws Exception {
//        AssignmentCreationDto assignmentCreationDto = new AssignmentCreationDto(
//                "",
//                "description",
//                LocalDateTime.now().plusDays(7)
//        );
//        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(classroomService).createAssignment(eq(1L), any(AssignmentCreationDto.class));
//        mvc.perform(post("/classes/1/assignments")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(assignmentCreationDto)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void getAssignments_assignmentsReturned() throws Exception {
//        when(classroomService.getAllClassAssignments(1L)).thenReturn(new ArrayList<AssignmentDetailsDto>());
//        mvc.perform(get("/classes/1/assignments")
//                        .with(csrf()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()", is(0)));
//    }
//
//    @Test
//    public void getAssignments_invalidClassId() throws Exception {
//        when(classroomService.getAllClassAssignments(1L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
//        mvc.perform(get("/classes/1/assignments")
//                        .with(csrf()))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void getAssignmentDetails_assignmentReturned() throws Exception {
//        when(classroomService.getAssignmentDetails(1L, 2L)).thenReturn(new AssignmentDetailsDto(
//                "title",
//                "description",
//                LocalDateTime.now(),
//                LocalDateTime.now()
//        ));
//        mvc.perform(get("/classes/1/assignments/2")
//                        .with(csrf()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title", is("title")));
//    }
//
//    @Test
//    public void getAssignmentDetails_invalidClassId() throws Exception {
//        when(classroomService.getAssignmentDetails(1L, 2L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
//        mvc.perform(get("/classes/1/assignments/2")
//                        .with(csrf()))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void getAssignmentDetails_invalidAssignmentId() throws Exception {
//        when(classroomService.getAssignmentDetails(1L, 2L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
//        mvc.perform(get("/classes/1/assignments/2")
//                        .with(csrf()))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void updateAssignmentDetails_detailsUpdated() throws Exception {
//        AssignmentDetailsUpdateDto newDetails = new AssignmentDetailsUpdateDto(
//                "assignment",
//                "description",
//                LocalDateTime.now().plusDays(7)
//        );
//        when(classroomService.updateAssignmentDetails(eq(1L), eq(2L), any(AssignmentDetailsUpdateDto.class))).thenReturn(new AssignmentDetailsDto(
//                "assignment",
//                "description",
//                LocalDateTime.now(),
//                LocalDateTime.now()
//        ));
//        mvc.perform(put("/classes/1/assignments/2")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newDetails)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void updateAssignmentDetails_invalidClassId() throws Exception {
//        AssignmentDetailsUpdateDto newDetails = new AssignmentDetailsUpdateDto(
//                "assignment",
//                "description",
//                LocalDateTime.now().plusDays(7)
//        );
//        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(classroomService).updateAssignmentDetails(eq(1L), eq(2L), any(AssignmentDetailsUpdateDto.class));
//        mvc.perform(put("/classes/1/assignments/2")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newDetails)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void updateAssignmentDetails_invalidAssignmentId() throws Exception {
//        AssignmentDetailsUpdateDto newDetails = new AssignmentDetailsUpdateDto(
//                "assignment",
//                "description",
//                LocalDateTime.now().plusDays(7)
//        );
//        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(classroomService).updateAssignmentDetails(eq(1L), eq(2L), any(AssignmentDetailsUpdateDto.class));
//        mvc.perform(put("/classes/1/assignments/2")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newDetails)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void updateAssignmentDetails_invalidNewDetails() throws Exception {
//        AssignmentDetailsUpdateDto newDetails = new AssignmentDetailsUpdateDto(
//                "",
//                "invalid description",
//                LocalDateTime.now().plusDays(7)
//        );
//        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(classroomService).updateAssignmentDetails(eq(1L), eq(2L), any(AssignmentDetailsUpdateDto.class));
//        mvc.perform(put("/classes/1/assignments/2")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newDetails)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void deleteAssignment_assignmentDeleted() throws Exception {
//        doNothing().when(classroomService).deleteAssignment(1L, 2L);
//        mvc.perform(delete("/classes/1/assignments/2")
//                        .with(csrf()))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void deleteAssignment_invalidClassId() throws Exception {
//        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(classroomService).deleteAssignment(1L, 2L);
//        mvc.perform(delete("/classes/1/assignments/2")
//                        .with(csrf()))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void deleteAssignment_invalidAssignmentId() throws Exception {
//        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(classroomService).deleteAssignment(1L, 2L);
//        mvc.perform(delete("/classes/1/assignments/2")
//                        .with(csrf()))
//                .andExpect(status().isNotFound());
//    }
//}
