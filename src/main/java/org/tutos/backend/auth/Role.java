package org.tutos.backend.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.tutos.backend.auth.utils.AuthorityLevel;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID roleId;
    private AuthorityLevel authority;

    public Role(UUID roleId, AuthorityLevel authority) {
        this.setRoleId(roleId);
        this.setAuthority(authority);
    }

    public Role(AuthorityLevel authority) {
        this.setAuthority(authority);
    }

    public Role() {
    }

    @Override
    public String getAuthority() {
        return authority.toString();
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public void setAuthority(AuthorityLevel authority) {
        this.authority = authority;
    }
}
