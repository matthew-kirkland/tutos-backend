package org.lms.backend.user;

import org.lms.backend.classroom.dto.AssignmentDto;
import org.lms.backend.classroom.dto.ClassDto;
import org.lms.backend.classroom.dto.ClassroomDtoMapper;
import org.lms.backend.common.exception.InvalidUserTypeException;
import org.lms.backend.common.exception.ValidationException;
import org.lms.backend.user.dto.UserDto;
import org.lms.backend.user.dto.UserDtoMapper;
import org.lms.backend.user.entity.Student;
import org.lms.backend.user.entity.Tutor;
import org.lms.backend.user.entity.User;
import org.lms.backend.user.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final ClassroomDtoMapper classroomDtoMapper;

    public UserService(
            UserRepository userRepository,
            UserDtoMapper userDtoMapper,
            ClassroomDtoMapper classroomDtoMapper
    ) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
        this.classroomDtoMapper = classroomDtoMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username " + username + " not valid")
        );
    }

    public List<UserDto.Details> getAllUsers() {
        return userRepository.findAll().stream().map(userDtoMapper::toDetailsDto).toList();
    }

    public UserDto.Details getUserDetails(Long userId) {
        return userDtoMapper.toDetailsDto(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId)));
    }

    @Transactional
    public UserDto.Details updateUserDetails(Long userId, UserDto.DetailsUpdate dto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // New details cannot contain an existing email or phone number
        if (userRepository.existsByEmail(dto.email())) {
            throw new ValidationException("Email already in use");
        }
        if (userRepository.existsByPhone(dto.phone())) {
            throw new ValidationException("Phone number already in use");
        }

        existingUser.updateUserDetails(
                dto.email(),
                dto.phone(),
                dto.nameFirst(),
                dto.nameLast(),
                dto.dob()
        );
        userRepository.save(existingUser);
        return userDtoMapper.toDetailsDto(existingUser);
    }

    public List<ClassDto.Details> getUserLessons(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (!(existingUser instanceof Student)) {
            throw new InvalidUserTypeException("User " + userId + " is not a learner and does not have lessons");
        }

        return ((Student) existingUser).getLessons().stream().map(classroomDtoMapper::toDetailsDto).toList();
    }

    public List<ClassDto.Details> getUserClasses(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (!(existingUser instanceof Tutor)) {
            throw new InvalidUserTypeException("User " + userId + " is not a teacher and does not have classes");
        }

        return ((Tutor) existingUser).getClasses().stream().map(classroomDtoMapper::toDetailsDto).toList();
    }

    public List<AssignmentDto.Details> getUserAssignments(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (!(existingUser instanceof Student)) {
            throw new InvalidUserTypeException("User " + userId + " is not a learner and does not have assignments");
        }

        return ((Student) existingUser).getAssignments().stream().map(classroomDtoMapper::toDetailsDto).toList();
    }

    @Transactional
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(existingUser);
    }
}
