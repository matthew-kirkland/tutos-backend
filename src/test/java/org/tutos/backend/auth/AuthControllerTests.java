//package org.lms.backend.auth;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.lms.backend.auth.dto.*;
//import org.lms.backend.user.UserRepository;
//import org.lms.backend.user.dto.UserDetailsDto;
//import org.lms.backend.user.entity.Admin;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.time.LocalDate;
//
//import static org.hamcrest.Matchers.is;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(AuthController.class)
//@WithMockUser(username = "testuser", roles = {"MASTER"})
//public class AuthControllerTests {
//    @Autowired
//    MockMvc mvc;
//    @Autowired
//    ObjectMapper objectMapper;
//    @MockitoBean
//    AuthService authService;
//    @MockitoBean
//    UserRepository userRepository;
//    @MockitoBean
//    RoleRepository roleRepository;
//
//    @Test
//    public void registerStudent_studentRegistered() throws Exception {
//        StudentCreationDto studentCreationDto = new StudentCreationDto(
//                "rosemarie_murray@hotmail.com",
//                "studentPass1",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17),
//                "Blakehurst High School",
//                9
//        );
//        UserDetailsDto info = new UserDetailsDto(
//                "rosemarie_murray@hotmail.com",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        );
//        when(authService.registerStudent(any(StudentCreationDto.class))).thenReturn(info);
//        mvc.perform(post("/auth/register/student")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(studentCreationDto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.email", is(info.email())))
//                .andExpect(jsonPath("$.phone", is(info.phone())))
//                .andExpect(jsonPath("$.nameFirst", is(info.nameFirst())))
//                .andExpect(jsonPath("$.nameLast", is(info.nameLast())));
//    }
//
//    @Test
//    public void registerStudent_invalidDetails() throws Exception {
//        StudentCreationDto studentCreationDto = new StudentCreationDto(
//                "rosemarie_murrayhotmail.com",
//                "studentPass1",
//                "123467890",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17),
//                "Blakehurst High School",
//                9
//
//        );
//        when(authService.registerStudent(any(StudentCreationDto.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
//        mvc.perform(post("/auth/register/student")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(studentCreationDto)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void registerParent_parentRegistered() throws Exception {
//        ParentCreationDto parentCreationDto = new ParentCreationDto(
//                "rosemarie_murray@hotmail.com",
//                "parentPass1",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        );
//        UserDetailsDto info = new UserDetailsDto(
//                "rosemarie_murray@hotmail.com",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        );
//        when(authService.registerParent(any(ParentCreationDto.class))).thenReturn(info);
//        mvc.perform(post("/auth/register/parent")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(parentCreationDto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.email", is(info.email())))
//                .andExpect(jsonPath("$.phone", is(info.phone())))
//                .andExpect(jsonPath("$.nameFirst", is(info.nameFirst())))
//                .andExpect(jsonPath("$.nameLast", is(info.nameLast())));
//    }
//
//    @Test
//    public void registerParent_invalidDetails() throws Exception {
//        ParentCreationDto parentCreationDto = new ParentCreationDto(
//                "rosemarie_murrayhotmail.com",
//                "parentPass1",
//                "123467890",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        );
//        when(authService.registerParent(any(ParentCreationDto.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
//        mvc.perform(post("/auth/register/parent")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(parentCreationDto)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void registerTutor_tutorRegistered() throws Exception {
//        TutorCreationDto tutorCreationDto = new TutorCreationDto(
//                "rosemarie_murray@hotmail.com",
//                "tutorPass1",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        );
//        UserDetailsDto info = new UserDetailsDto(
//                "rosemarie_murray@hotmail.com",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        );
//        when(authService.registerTutor(any(TutorCreationDto.class))).thenReturn(info);
//        mvc.perform(post("/auth/register/tutor")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(tutorCreationDto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.email", is(info.email())))
//                .andExpect(jsonPath("$.phone", is(info.phone())))
//                .andExpect(jsonPath("$.nameFirst", is(info.nameFirst())))
//                .andExpect(jsonPath("$.nameLast", is(info.nameLast())));
//    }
//
//    @Test
//    public void registerTutor_invalidDetails() throws Exception {
//        TutorCreationDto tutorCreationDto = new TutorCreationDto(
//                "rosemarie_murrayhotmail.com",
//                "tutorPass1",
//                "123467890",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        );
//        when(authService.registerTutor(any(TutorCreationDto.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
//        mvc.perform(post("/auth/register/tutor")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(tutorCreationDto)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void registerAdmin_adminRegistered() throws Exception {
//        AdminCreationDto adminCreationDto = new AdminCreationDto(
//                "rosemarie_murray@hotmail.com",
//                "adminPass1",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        );
//        UserDetailsDto info = new UserDetailsDto(
//                "rosemarie_murray@hotmail.com",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        );
//        when(authService.registerAdmin(any(AdminCreationDto.class))).thenReturn(info);
//        mvc.perform(post("/auth/register/admin")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(adminCreationDto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.email", is(info.email())))
//                .andExpect(jsonPath("$.phone", is(info.phone())))
//                .andExpect(jsonPath("$.nameFirst", is(info.nameFirst())))
//                .andExpect(jsonPath("$.nameLast", is(info.nameLast())));
//    }
//
//    @Test
//    public void registerAdmin_invalidDetails() throws Exception {
//        AdminCreationDto adminCreationDto = new AdminCreationDto(
//                "rosemarie_murrayhotmail.com",
//                "adminPass1",
//                "123467890",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        );
//        when(authService.registerAdmin(any(AdminCreationDto.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
//        mvc.perform(post("/auth/register/admin")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(adminCreationDto)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void registerMaster_studentRegistered() throws Exception {
//        MasterCreationDto masterCreationDto = new MasterCreationDto(
//                "rosemarie_murray@hotmail.com",
//                "masterPass1",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        );
//        UserDetailsDto info = new UserDetailsDto(
//                "rosemarie_murray@hotmail.com",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        );
//        when(authService.registerMaster(any(MasterCreationDto.class))).thenReturn(info);
//        mvc.perform(post("/auth/register/master")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(masterCreationDto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.email", is(info.email())))
//                .andExpect(jsonPath("$.phone", is(info.phone())))
//                .andExpect(jsonPath("$.nameFirst", is(info.nameFirst())))
//                .andExpect(jsonPath("$.nameLast", is(info.nameLast())));
//    }
//
//    @Test
//    public void registerMaster_invalidDetails() throws Exception {
//        MasterCreationDto masterCreationDto = new MasterCreationDto(
//                "rosemarie_murrayhotmail.com",
//                "masterPass1",
//                "123467890",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        );
//        when(authService.registerMaster(any(MasterCreationDto.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
//        mvc.perform(post("/auth/register/master")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(masterCreationDto)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void loginUser_correctDetails() throws Exception {
//        LoginInputDto loginInputDto = new LoginInputDto("abc", "123");
//        when(authService.loginUser(any(String.class), any(String.class))).thenReturn(new LoginResponseDto(new Admin(), "token"));
//        mvc.perform(post("/auth/login")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(loginInputDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.jwt", is("token")));
//    }
//
//    @Test
//    public void loginUser_incorrectDetails() throws Exception {
//        LoginInputDto loginInputDto = new LoginInputDto("abc", "123");
//        when(authService.loginUser(any(String.class), any(String.class))).thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED));
//        mvc.perform(post("/auth/login")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(loginInputDto)))
//                .andExpect(status().isUnauthorized());
//    }
//}
