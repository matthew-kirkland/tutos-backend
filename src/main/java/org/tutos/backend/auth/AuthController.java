package org.tutos.backend.auth;

import jakarta.validation.Valid;
import org.tutos.backend.auth.dto.*;
import org.tutos.backend.auth.dto.*;
import org.tutos.backend.user.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register/student")
    public UserDto.Details registerStudent(@RequestBody @Valid StudentCreationDto studentCreationDto) {
        return authService.registerStudent(studentCreationDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register/parent")
    public UserDto.Details registerParent(@RequestBody @Valid ParentCreationDto parentCreationDto) {
        return authService.registerParent(parentCreationDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register/tutor")
    public UserDto.Details registerTutor(@RequestBody @Valid TutorCreationDto tutorCreationDto) {
        return authService.registerTutor(tutorCreationDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register/admin")
    public UserDto.Details registerAdmin(@RequestBody @Valid AdminCreationDto adminCreationDto) {
        return authService.registerAdmin(adminCreationDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register/master")
    public UserDto.Details registerMaster(@RequestBody @Valid MasterCreationDto masterCreationDto) {
        return authService.registerMaster(masterCreationDto);
    }

    @PostMapping("/login")
    public LoginResponseDto loginUser(@RequestBody @Valid LoginInputDto loginInputDto) {
        return authService.loginUser(loginInputDto.username(), loginInputDto.password());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/logout")
    public void logoutUser() {
        authService.logoutUser();
    }

    // TODO add something about changing password
}
