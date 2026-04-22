package org.tutos.backend.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.tutos.backend.classroom.dto.AssignmentDto;
import org.tutos.backend.classroom.dto.ClassDto;
import org.tutos.backend.user.dto.UserDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
    @GetMapping()
    public List<UserDto.Details> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserDto.Details getUserDetails(@PathVariable Long userId) {
        return userService.getUserDetails(userId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
    @PutMapping("/{userId}")
    public UserDto.Details updateUserDetails(@PathVariable Long userId, @RequestBody UserDto.DetailsUpdate newDetails) {
        return userService.updateUserDetails(userId, newDetails);
    }

    @GetMapping("/{userId}/lessons")
    public List<ClassDto.Details> getUserLessons(@PathVariable Long userId) {
        return userService.getUserLessons(userId);
    }

    @GetMapping("/{userId}/classes")
    public List<ClassDto.Details> getUserClasses(@PathVariable Long userId) {
        return userService.getUserClasses(userId);
    }

    @GetMapping("/{userId}/assignments")
    public List<AssignmentDto.Details> getUserAssignments(@PathVariable Long userId) {
        return userService.getUserAssignments(userId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
