package org.lms.backend;

import org.lms.backend.auth.Role;
import org.lms.backend.auth.RoleRepository;
import org.lms.backend.auth.utils.AuthorityLevel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository) {
        return args -> {
            roleRepository.save(new Role(AuthorityLevel.LEARNER));
            roleRepository.save(new Role(AuthorityLevel.TEACHER));
            roleRepository.save(new Role(AuthorityLevel.ADMIN));
            roleRepository.save(new Role(AuthorityLevel.MASTER));
            // potentially add some starter users
        };
    }
}
