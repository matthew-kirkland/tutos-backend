package org.lms.backend.auth;

import org.lms.backend.auth.dto.*;
import org.lms.backend.auth.exception.DuplicateUserException;
import org.lms.backend.auth.exception.InvalidCredentialsException;
import org.lms.backend.auth.utils.AuthorityLevel;
import org.lms.backend.user.UserRepository;
import org.lms.backend.user.dto.UserDto;
import org.lms.backend.user.dto.UserDtoMapper;
import org.lms.backend.user.entity.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserDtoMapper userDtoMapper;

    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            TokenService tokenService,
            UserDtoMapper userDtoMapper
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userDtoMapper = userDtoMapper;
    }

    @Transactional
    public UserDto.Details registerStudent(StudentCreationDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new DuplicateUserException("Email already in use");
        }
        if (userRepository.existsByPhone(dto.phone())) {
            throw new DuplicateUserException("Phone number already in use");
        }
        String hashedPassword = passwordEncoder.encode(dto.password());
        Student student = new Student(
                dto.email(),
                dto.email(),
                hashedPassword,
                dto.phone(),
                dto.nameFirst(),
                dto.nameLast(),
                dto.dob(),
                dto.school(),
                dto.schoolYear()
        );
        student.addAuthority(roleRepository.findByAuthority(AuthorityLevel.LEARNER).get());
        userRepository.save(student);
        return userDtoMapper.toDetailsDto(student);
    }

    @Transactional
    public UserDto.Details registerParent(ParentCreationDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new DuplicateUserException("Email already in use");
        }
        if (userRepository.existsByPhone(dto.phone())) {
            throw new DuplicateUserException("Phone number already in use");
        }
        String hashedPassword = passwordEncoder.encode(dto.password());
        Parent parent = new Parent(
                dto.email(),
                dto.email(),
                hashedPassword,
                dto.phone(),
                dto.nameFirst(),
                dto.nameLast(),
                dto.dob()
        );
        parent.addAuthority(roleRepository.findByAuthority(AuthorityLevel.LEARNER).get());
        userRepository.save(parent);
        return userDtoMapper.toDetailsDto(parent);
    }

    @Transactional
    public UserDto.Details registerTutor(TutorCreationDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new DuplicateUserException("Email already in use");
        }
        if (userRepository.existsByPhone(dto.phone())) {
            throw new DuplicateUserException("Phone number already in use");
        }
        String hashedPassword = passwordEncoder.encode(dto.password());
        Tutor tutor = new Tutor(
                dto.email(),
                dto.email(),
                hashedPassword,
                dto.phone(),
                dto.nameFirst(),
                dto.nameLast(),
                dto.dob()
        );
        tutor.addAuthority(roleRepository.findByAuthority(AuthorityLevel.TEACHER).get());
        userRepository.save(tutor);
        return userDtoMapper.toDetailsDto(tutor);
    }

    @Transactional
    public UserDto.Details registerAdmin(AdminCreationDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new DuplicateUserException("Email already in use");
        }
        if (userRepository.existsByPhone(dto.phone())) {
            throw new DuplicateUserException("Phone number already in use");
        }
        String hashedPassword = passwordEncoder.encode(dto.password());
        Admin admin = new Admin(
                dto.email(),
                dto.email(),
                hashedPassword,
                dto.phone(),
                dto.nameFirst(),
                dto.nameLast(),
                dto.dob()
        );
        admin.addAuthority(roleRepository.findByAuthority(AuthorityLevel.ADMIN).get());
        userRepository.save(admin);
        return userDtoMapper.toDetailsDto(admin);
    }

    @Transactional
    public UserDto.Details registerMaster(MasterCreationDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new DuplicateUserException("Email already in use");
        }
        if (userRepository.existsByPhone(dto.phone())) {
            throw new DuplicateUserException("Phone number already in use");
        }
        String hashedPassword = passwordEncoder.encode(dto.password());
        Master master = new Master(
                dto.email(),
                dto.email(),
                hashedPassword,
                dto.phone(),
                dto.nameFirst(),
                dto.nameLast(),
                dto.dob()
        );
        master.addAuthority(roleRepository.findByAuthority(AuthorityLevel.MASTER).get());
        userRepository.save(master);
        return userDtoMapper.toDetailsDto(master);
    }

    public LoginResponseDto loginUser(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            String jwt = tokenService.generateJwt(authentication);
            return new LoginResponseDto(userRepository.findByUsername(username).get(), jwt);
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Incorrect username or password");
        }
    }

    public void logoutUser() {
        // TODO
    }
}
