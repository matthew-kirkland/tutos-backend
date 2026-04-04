package org.lms.backend.classroom.entity;

import jakarta.persistence.*;
import org.lms.backend.calendar.entity.ClassSchedule;
import org.lms.backend.calendar.entity.ClassSession;
import org.lms.backend.user.entity.Student;
import org.lms.backend.user.entity.Tutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "classes")
public abstract class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long classId;
    private String title;
    private String description;
    @ManyToMany
    @JoinTable(
            name = "class_tutors",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "tutor_id")
    )
    private Set<Tutor> tutors;
    @OneToMany(mappedBy = "classBelongingTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignment> assignments = new ArrayList<Assignment>();
    @OneToMany(mappedBy = "classBelongingTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Announcement> announcements = new ArrayList<Announcement>();
    @OneToOne(mappedBy = "classOf", cascade = CascadeType.ALL)
    private ClassSchedule classSchedule;
    @OneToMany(mappedBy = "classOf")
    private List<ClassSession> classSessions;

    protected Class(
            String title,
            String description,
            Set<Tutor> tutors
    ) {
        this.setTitle(title);
        this.setDescription(description);
        this.tutors = tutors;
    }

    protected Class() {
    }

    public void updateClassDetails(
            String title,
            String description
    ) {
        this.setTitle(title);
        this.setDescription(description);
    }

    public void addTutor(Tutor tutor) {
        if (tutors.add(tutor)) {
            tutor.addClass(this);
        }
    }

    public void removeTutor(Tutor tutor) {
        if (tutors.remove(tutor)) {
            tutor.removeClass(this);
        }
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
        assignment.setClassBelongingTo(this);
    }

    public void removeAssignment(Assignment assignment) {
        assignments.remove(assignment);
    }

    public void addAnnouncement(Announcement announcement) {
        announcements.add(announcement);
        announcement.setClassBelongingTo(this);
    }

    public void removeAnnouncement(Announcement announcement) {
        announcements.remove(announcement);
    }

    public abstract void addStudent(Student user);

    public abstract void removeStudent(Student user);

    public Long getClassId() {
        return classId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Tutor> getTutors() {
        return tutors;
    }

    public abstract Set<Student> getStudents();

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public ClassSchedule getClassSchedule() {
        return classSchedule;
    }

    public void setClassSchedule(ClassSchedule classSchedule) {
        this.classSchedule = classSchedule;
    }

    public List<ClassSession> getClassSessions() {
        return classSessions;
    }

    public void setClassSessions(List<ClassSession> classSessions) {
        this.classSessions = classSessions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Class other)) return false;
        return classId != null && classId.equals(other.getClassId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
