package org.lms.backend.user.entity;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Master extends User {
    public Master(
            String username,
            String email,
            String password,
            String phone,
            String nameFirst,
            String nameLast,
            LocalDate dob
    ) {
        super(username, email, password, phone, nameFirst, nameLast, dob);
    }

    public Master() {
    }

    @Override
    public boolean isEmailNotif() {
        return false;
    }

    @Override
    public boolean isSmsNotif() {
        return false;
    }
}
