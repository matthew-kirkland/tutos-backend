package org.lms.backend.user.entity;

import jakarta.persistence.*;
import org.lms.backend.calendar.entity.ClassSession;
import org.lms.backend.classroom.entity.Assignment;
import org.lms.backend.classroom.entity.Class;
import org.lms.backend.user.exception.StudentAlreadyParentChildException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Student extends User {
    private String school;
    private int schoolYear;
    private boolean emailNotif;
    private boolean smsNotif;
    @ManyToMany
    @JoinTable(
            name = "student_assignments",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "assignment_id")
    )
    private List<Assignment> assignments = new ArrayList<Assignment>();
    @ManyToMany
    private Set<Class> lessons = new HashSet<Class>();
    @ManyToOne
    private Parent parent;
    @ManyToMany(mappedBy = "students")
    @OrderBy("startTime ASC")
    private List<ClassSession> classSessions = new ArrayList<ClassSession>();

    public Student(
            String username,
            String email,
            String password,
            String phone,
            String nameFirst,
            String nameLast,
            LocalDate dob,
            String school,
            int schoolYear
    ) {
        super(username, email, password, phone, nameFirst, nameLast, dob);
        this.setSchool(school);
        this.setSchoolYear(schoolYear);
        this.setEmailNotif(true);
        this.setSmsNotif(true);
    }

    public Student() {
    }

    public void addLesson(Class lesson) {
        if (lessons.add(lesson)) {
            lesson.addStudent(this);
        }
    }

    public void addLessonInternal(Class lesson) {
        lessons.add(lesson);
    }

    public void removeLesson(Class lesson) {
        if (lessons.remove(lesson)) {
            lesson.removeStudent(this);
        }
    }

    public void removeLessonInternal(Class lesson) {
        lessons.remove(lesson);
    }

    public void addParent(Parent parent) {
        if (this.parent != null) {
            throw new StudentAlreadyParentChildException(
                    "Student " + getUserId() + " is already a child of another parent " + parent.getUserId()
            );
        }

        this.parent = parent;
        parent.addChildInternal(this);
    }

    public void removeParent(Parent parent) {
        if (this.parent != null && this.parent.equals(parent)) {
            this.parent = null;
            parent.removeChildInternal(this);
        }
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int year) {
        this.schoolYear = year;
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

    public Set<Class> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Class> lessons) {
        this.lessons = lessons;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<ClassSession> getClassSessions() {
        return classSessions;
    }

    public void setClassSessions(List<ClassSession> upcomingSessions) {
        this.classSessions = upcomingSessions;
    }
}
