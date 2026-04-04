package org.tutos.backend.calendar.entity;

import jakarta.persistence.*;
import org.tutos.backend.classroom.entity.Class;
import org.tutos.backend.user.entity.Student;
import org.tutos.backend.user.entity.Tutor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "class_sessions")
public class ClassSession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long classSessionId;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class classOf;
    @ManyToOne
    @JoinColumn(nullable = true)
    private ClassSchedule classSchedule;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @ManyToMany
    @JoinTable(
            name = "session_active_tutors",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "tutor_id")
    )
    private Set<Tutor> activeTutors;
    @ManyToMany
    @JoinTable(
            name = "session_observer_tutors",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "tutor_id")
    )
    private Set<Tutor> observerTutors;
    @ManyToMany
    @JoinTable(
            name = "session_students",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students;
    @OneToMany(mappedBy = "classSession", cascade = CascadeType.ALL)
    private Set<StudentAttendanceRecord> attendanceRecords = new HashSet<StudentAttendanceRecord>();
    private ClassSessionStatus status;
    private String notes;

    public ClassSession(
            Class classOf,
            ClassSchedule classSchedule,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Set<Tutor> tutors,
            Set<Student> students,
            ClassSessionStatus status
    ) {
        this.setClassOf(classOf);
        this.setClassSchedule(classSchedule);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setActiveTutors(tutors);
        this.setStudents(students);
        this.setStatus(status);
    }

    public ClassSession() {
    }

    public void setReplacementTutors(Set<Tutor> tutors) {
        // Remove replaced tutors from activeTutors
        Set<Tutor> replacedTutors = new HashSet<Tutor>(this.activeTutors);
        replacedTutors.retainAll(tutors);
        this.activeTutors.removeAll(replacedTutors);

        // Add replaced tutors to observerTutors
        this.observerTutors.addAll(replacedTutors);

        // Add replacement tutors to activeTutors
        this.activeTutors.addAll(tutors);
    }

    public Long getClassSessionId() {
        return classSessionId;
    }

    public void setClassSessionId(Long classSessionId) {
        this.classSessionId = classSessionId;
    }

    public Class getClassOf() {
        return classOf;
    }

    public void setClassOf(Class classOf) {
        this.classOf = classOf;
    }

    public ClassSchedule getClassSchedule() {
        return classSchedule;
    }

    public void setClassSchedule(ClassSchedule classSchedule) {
        this.classSchedule = classSchedule;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Set<Tutor> getActiveTutors() {
        return activeTutors;
    }

    public void setActiveTutors(Set<Tutor> tutors) {
        this.activeTutors = tutors;
    }

    public Set<Tutor> getObserverTutors() {
        return observerTutors;
    }

    public void setObserverTutors(Set<Tutor> tutors) {
        this.observerTutors = tutors;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<StudentAttendanceRecord> getAttendanceRecords() {
        return attendanceRecords;
    }

    public void setAttendanceRecords(Set<StudentAttendanceRecord> attendanceRecords) {
        this.attendanceRecords = attendanceRecords;
    }

    public ClassSessionStatus getStatus() {
        return status;
    }

    public void setStatus(ClassSessionStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
