package org.lms.backend.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.lms.backend.auth.utils.AuthorityLevel;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;
    private AuthorityLevel authority;

    public Role(Long roleId, AuthorityLevel authority) {
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setAuthority(AuthorityLevel authority) {
        this.authority = authority;
    }
}
