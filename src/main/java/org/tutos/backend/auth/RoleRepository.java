package org.tutos.backend.auth;

import org.tutos.backend.auth.utils.AuthorityLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByAuthority(AuthorityLevel authority);
}
