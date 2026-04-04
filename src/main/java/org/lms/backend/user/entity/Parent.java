package org.lms.backend.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Parent extends User {
    private boolean emailNotif;
    private boolean smsNotif;
    @OneToMany
    private Set<Student> children = new HashSet<Student>();

    public Parent(
            String username,
            String email,
            String password,
            String phone,
            String nameFirst,
            String nameLast,
            LocalDate dob
    ) {
        super(username, email, password, phone, nameFirst, nameLast, dob);
        this.setEmailNotif(true);
        this.setSmsNotif(true);
    }

    public Parent() {
    }

    public void addChildInternal(Student student) {
        children.add(student);
    }

    public void removeChildInternal(Student student) {
        children.remove(student);
    }

    @Override
    public boolean isEmailNotif() {
        return emailNotif;
    }

    public void setEmailNotif(boolean emailNotif) {
        this.emailNotif = emailNotif;
    }

    @Override
    public boolean isSmsNotif() {
        return smsNotif;
    }

    public void setSmsNotif(boolean smsNotif) {
        this.smsNotif = smsNotif;
    }

    public Set<Student> getChildren() {
        return children;
    }

    public void setChildren(Set<Student> children) {
        this.children = children;
    }
}
