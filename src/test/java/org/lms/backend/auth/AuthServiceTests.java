//package org.lms.backend.auth;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.lms.backend.auth.dto.*;
//import org.lms.backend.auth.exception.InvalidCredentialsException;
//import org.lms.backend.auth.utils.AuthorityLevel;
//import org.lms.backend.user.UserRepository;
//import org.lms.backend.user.dto.UserDetailsDto;
//import org.lms.backend.user.dto.UserDtoMapper;
//import org.lms.backend.user.entity.Student;
//import org.lms.backend.user.entity.User;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class AuthServiceTests {
//    @Mock
//    UserRepository userRepository;
//    @Mock
//    RoleRepository roleRepository;
//    @Mock
//    UserDtoMapper userDtoMapper;
//    @Mock
//    AuthenticationManager authenticationManager;
//    @Mock
//    PasswordEncoder passwordEncoder;
//    @Mock
//    TokenService tokenService;
//    @InjectMocks
//    AuthService authService;
//
//    @Test
//    public void registerStudent_studentRegistered() {
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
//        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
//        when(roleRepository.findByAuthority(AuthorityLevel.LEARNER)).thenReturn(Optional.of(new Role(AuthorityLevel.LEARNER)));
//        when(userDtoMapper.toDto(any(User.class))).thenReturn(new UserDetailsDto(
//                "rosemarie_murray@hotmail.com",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        ));
//
//        UserDetailsDto result = authService.registerStudent(studentCreationDto);
//        assertEquals(studentCreationDto.email(), result.email());
//        verify(userRepository).save(any(User.class));
//    }
//
//    @Test
//    public void registerParent_parentRegistered() {
//        ParentCreationDto parentCreationDto = new ParentCreationDto(
//                "rosemarie_murray@hotmail.com",
//                "parentPass1",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        );
//        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
//        when(roleRepository.findByAuthority(AuthorityLevel.LEARNER)).thenReturn(Optional.of(new Role(AuthorityLevel.LEARNER)));
//        when(userDtoMapper.toDto(any(User.class))).thenReturn(new UserDetailsDto(
//                "rosemarie_murray@hotmail.com",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        ));
//
//        UserDetailsDto result = authService.registerParent(parentCreationDto);
//        assertEquals(parentCreationDto.email(), result.email());
//        verify(userRepository).save(any(User.class));
//    }
//
//    @Test
//    public void registerTutor_tutorRegistered() {
//        TutorCreationDto tutorCreationDto = new TutorCreationDto(
//                "rosemarie_murray@hotmail.com",
//                "tutorPass1",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        );
//        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
//        when(roleRepository.findByAuthority(AuthorityLevel.TEACHER)).thenReturn(Optional.of(new Role(AuthorityLevel.LEARNER)));
//        when(userDtoMapper.toDto(any(User.class))).thenReturn(new UserDetailsDto(
//                "rosemarie_murray@hotmail.com",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        ));
//
//        UserDetailsDto result = authService.registerTutor(tutorCreationDto);
//        assertEquals(tutorCreationDto.email(), result.email());
//        verify(userRepository).save(any(User.class));
//    }
//
//    @Test
//    public void registerAdmin_adminRegistered() {
//        AdminCreationDto adminCreationDto = new AdminCreationDto(
//                "rosemarie_murray@hotmail.com",
//                "adminPass1",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        );
//        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
//        when(roleRepository.findByAuthority(AuthorityLevel.ADMIN)).thenReturn(Optional.of(new Role(AuthorityLevel.LEARNER)));
//        when(userDtoMapper.toDto(any(User.class))).thenReturn(new UserDetailsDto(
//                "rosemarie_murray@hotmail.com",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        ));
//
//        UserDetailsDto result = authService.registerAdmin(adminCreationDto);
//        assertEquals(adminCreationDto.email(), result.email());
//        verify(userRepository).save(any(User.class));
//    }
//
//    @Test
//    public void registerMaster_masterRegistered() {
//        MasterCreationDto masterCreationDto = new MasterCreationDto(
//                "rosemarie_murray@hotmail.com",
//                "masterPass1",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        );
//        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
//        when(roleRepository.findByAuthority(AuthorityLevel.MASTER)).thenReturn(Optional.of(new Role(AuthorityLevel.LEARNER)));
//        when(userDtoMapper.toDto(any(User.class))).thenReturn(new UserDetailsDto(
//                "rosemarie_murray@hotmail.com",
//                "+61400000000",
//                "Rosemarie",
//                "Murray",
//                LocalDate.of(1987, 8, 17)
//        ));
//
//        UserDetailsDto result = authService.registerMaster(masterCreationDto);
//        assertEquals(masterCreationDto.email(), result.email());
//        verify(userRepository).save(any(User.class));
//    }
//
//    @Test
//    public void loginUser_success() {
//        String username = "john";
//        String password = "password";
//        String jwt = "mocked-jwt";
//        Authentication authentication = mock(Authentication.class);
//        User user = new Student();
//
//        when(authenticationManager.authenticate(any())).thenReturn(authentication);
//        when(tokenService.generateJwt(authentication)).thenReturn(jwt);
//        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
//
//        LoginResponseDto result = authService.loginUser(username, password);
//        assertEquals(user, result.user());
//        assertEquals(jwt, result.jwt());
//        verify(authenticationManager).authenticate(any());
//        verify(tokenService).generateJwt(authentication);
//    }
//
//    @Test
//    public void loginUser_invalidCredentials() {
//        String username = "john";
//        String password = "wrong";
//        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Bad credentials"));
//
//        assertThrows(InvalidCredentialsException.class, () -> authService.loginUser(username, password));
//    }
//}
