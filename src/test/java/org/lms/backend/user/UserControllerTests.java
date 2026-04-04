//package org.lms.backend.user;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.lms.backend.auth.RoleRepository;
//import org.lms.backend.classroom.dto.AssignmentDetailsDto;
//import org.lms.backend.classroom.dto.ClassDetailsDto;
//import org.lms.backend.common.exception.ValidationException;
//import org.lms.backend.user.dto.UserDetailsDto;
//import org.lms.backend.user.dto.UserDetailsUpdateDto;
//import org.lms.backend.user.dto.UserDtoMapper;
//import org.lms.backend.user.entity.Parent;
//import org.lms.backend.user.entity.Student;
//import org.lms.backend.user.entity.User;
//import org.lms.backend.user.exception.UserNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.Matchers.is;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(UserController.class)
//@WithMockUser(username = "testuser", roles = {"MASTER"})
//public class UserControllerTests {
//    @Autowired
//    MockMvc mvc;
//    @Autowired
//    ObjectMapper objectMapper;
//    @MockitoBean
//    UserDtoMapper userDtoMapper;
//    @MockitoBean
//    UserService userService;
//    @MockitoBean
//    UserRepository userRepository;
//    @MockitoBean
//    RoleRepository roleRepository;
//
//    @Test
//    public void getAllUsers_returnsAllUsers() throws Exception {
//        List<User> users = new ArrayList<User>();
//        users.add(new Student(
//                "student1",
//                "rosemarie_murray@hotmail.com",
//                "studentPass1",
//                "0400-000-000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17),
//                "Blakehurst High School",
//                9
//        ));
//        users.add(new Parent(
//                "parent1",
//                "lisa_mohr@aol.com",
//                "parentPass1",
//                "0400-000-001",
//                "Lisa",
//                "Mohr",
//                LocalDate.of(1990, 10, 7)
//        ));
//        List<UserDetailsDto> details = List.of(
//                new UserDetailsDto(
//                        "rosemarie_murray@hotmail.com",
//                        "0400-000-000",
//                        "Rosemarie",
//                        "Murray",
//                        LocalDate.of(1987, 8, 17)
//                ),
//                new UserDetailsDto(
//                        "lisa_mohr@aol.com",
//                        "0400-000-001",
//                        "Lisa",
//                        "Mohr",
//                        LocalDate.of(1990, 10, 7)
//                )
//        );
//        when(userService.getAllUsers()).thenReturn(details);
//        mvc.perform(get("/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()", is(users.size())));
//    }
//
//    @Test
//    public void getUserDetails_returnsUser() throws Exception {
//        when(userService.getUserDetails(1L)).thenReturn(new UserDetailsDto(
//                "yvette_dubuque@hotmail.com",
//                "0400-000-005",
//                "Yvette",
//                "DuBuque",
//                LocalDate.of(1999, 11, 23)
//        ));
//
//        mvc.perform(get("/users/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email", is("yvette_dubuque@hotmail.com")))
//                .andExpect(jsonPath("$.phone", is("0400-000-005")))
//                .andExpect(jsonPath("$.nameFirst", is("Yvette")))
//                .andExpect(jsonPath("$.nameLast", is("DuBuque")));
//    }
//
//    @Test
//    public void getUserDetails_invalidUserId() throws Exception {
//        when(userService.getUserDetails(1L)).thenThrow(new UserNotFoundException(1L));
//        mvc.perform(get("/users/1"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void updateUserDetails_detailsUpdated() throws Exception {
//        UserDetailsUpdateDto newDetails = new UserDetailsUpdateDto(
//                "yvette_dubuque@hotmail.com",
//                "0400-000-005",
//                "Yvette",
//                "DuBuque",
//                LocalDate.of(1999, 11, 23)
//        );
//        UserDetailsDto updated = new UserDetailsDto(
//                "yvette_dubuque@hotmail.com",
//                "0400-000-005",
//                "Yvette",
//                "DuBuque",
//                LocalDate.of(1999, 11, 23)
//        );
//        when(userService.updateUserDetails(eq(1L), any(UserDetailsUpdateDto.class))).thenReturn(updated);
//
//        mvc.perform(put("/users/1")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newDetails)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email", is(updated.email())))
//                .andExpect(jsonPath("$.phone", is(updated.phone())))
//                .andExpect(jsonPath("$.nameFirst", is(updated.nameFirst())))
//                .andExpect(jsonPath("$.nameLast", is(updated.nameLast())));
//    }
//
//    @Test
//    public void updateUserDetails_invalidUserId() throws Exception {
//        UserDetailsUpdateDto newDetails = new UserDetailsUpdateDto(
//                "yvette_dubuque@hotmail.com",
//                "0400-000-005",
//                "Yvette",
//                "DuBuque",
//                LocalDate.of(1999, 11, 23)
//        );
//        when(userService.updateUserDetails(eq(1L), any(UserDetailsUpdateDto.class))).thenThrow(new UserNotFoundException(1L));
//        mvc.perform(put("/users/1")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newDetails)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void updateUserDetails_invalidDetails() throws Exception {
//        UserDetailsUpdateDto newDetails = new UserDetailsUpdateDto(
//                "yvette_dubuque@hotmail.com",
//                "0400-000-005",
//                "Yvette",
//                "DuBuque",
//                LocalDate.of(1999, 11, 23)
//        );
//        when(userService.updateUserDetails(eq(1L), any(UserDetailsUpdateDto.class))).thenThrow(new ValidationException("Invalid new details"));
//        mvc.perform(put("/users/1")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newDetails)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void getUserLessons_lessonsReturned() throws Exception {
//        when(userService.getUserLessons(1L)).thenReturn(new ArrayList<ClassDetailsDto>());
//        mvc.perform(get("/users/1/lessons")
//                        .with(csrf()))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void getUserLessons_invalidUserId() throws Exception {
//        when(userService.getUserLessons(1L)).thenThrow(new UserNotFoundException(1L));
//        mvc.perform(get("/users/1/lessons")
//                        .with(csrf()))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void getUserClasses_classesReturned() throws Exception {
//        when(userService.getUserClasses(1L)).thenReturn(new ArrayList<ClassDetailsDto>());
//        mvc.perform(get("/users/1/classes")
//                        .with(csrf()))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void getUserClasses_invalidUserId() throws Exception {
//        when(userService.getUserClasses(1L)).thenThrow(new UserNotFoundException(1L));
//        mvc.perform(get("/users/1/classes")
//                        .with(csrf()))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void getUserAssignments_assignmentsReturned() throws Exception {
//        when(userService.getUserAssignments(1L)).thenReturn(new ArrayList<AssignmentDetailsDto>());
//        mvc.perform(get("/users/1/assignments")
//                        .with(csrf()))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void getUserAssignments_invalidUserId() throws Exception {
//        when(userService.getUserAssignments(1L)).thenThrow(new UserNotFoundException(1L));
//        mvc.perform(get("/users/1/assignments")
//                        .with(csrf()))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void deleteUser_userDeleted() throws Exception {
//        doNothing().when(userService).deleteUser(1L);
//        mvc.perform(delete("/users/1")
//                        .with(csrf()))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void deleteUser_invalidUserId() throws Exception {
//        doThrow(new UserNotFoundException(1L)).when(userService).deleteUser(1L);
//        mvc.perform(delete("/users/1")
//                        .with(csrf()))
//                .andExpect(status().isNotFound());
//    }
//}
