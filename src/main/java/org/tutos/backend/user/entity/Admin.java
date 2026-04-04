package org.tutos.backend.user.entity;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Admin extends User {
    public Admin(
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

    public Admin() {
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
