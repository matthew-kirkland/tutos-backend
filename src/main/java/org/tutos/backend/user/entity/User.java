package org.tutos.backend.user.entity;

import jakarta.persistence.*;
import org.tutos.backend.auth.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    private String phone;
    private String nameFirst;
    private String nameLast;
    private LocalDate dob;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> authorities;

    protected User(
            String username,
            String email,
            String password,
            String phone,
            String nameFirst,
            String nameLast,
            LocalDate dob
    ) {
        this.setUsername(username);
        this.setEmail(email);
        this.setPassword(password);
        this.setPhone(phone);
        this.setNameFirst(nameFirst);
        this.setNameLast(nameLast);
        this.setDob(dob);
        authorities = new HashSet<Role>();
    }

    protected User() {
    }

    public void updateUserDetails(
            String email,
            String phone,
            String nameFirst,
            String nameLast,
            LocalDate dob
    ) {
        this.setEmail(email);
        this.setPhone(phone);
        this.setNameFirst(nameFirst);
        this.setNameLast(nameLast);
        this.setDob(dob);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addAuthority(Role role) {
        authorities.add(role);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNameFirst() {
        return nameFirst;
    }

    public void setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
    }

    public String getNameLast() {
        return nameLast;
    }

    public void setNameLast(String nameLast) {
        this.nameLast = nameLast;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public abstract boolean isEmailNotif();

    public abstract boolean isSmsNotif();

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User other)) return false;
        return userId != null && userId.equals(other.getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
