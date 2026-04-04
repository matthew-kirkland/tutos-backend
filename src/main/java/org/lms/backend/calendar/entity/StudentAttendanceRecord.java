package org.lms.backend.calendar.entity;


import jakarta.persistence.*;
import org.lms.backend.user.entity.Student;
import org.lms.backend.user.entity.Tutor;

@Entity
public class StudentAttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long studentAttendanceRecordId;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private ClassSession classSession;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    private StudentAttendanceStatus status;
    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor markedBy;
    private String notes;

    public StudentAttendanceRecord(ClassSession classSession, Student student, Tutor markedBy) {
        this.setClassSession(classSession);
        this.setStudent(student);
        this.setMarkedBy(markedBy);
        this.status = StudentAttendanceStatus.UNMARKED;
    }

    public StudentAttendanceRecord() {
    }


    public Long getStudentAttendanceRecordId() {
        return studentAttendanceRecordId;
    }

    public void setStudentAttendanceRecordId(Long studentAttendanceRecordId) {
        this.studentAttendanceRecordId = studentAttendanceRecordId;
    }

    public ClassSession getClassSession() {
        return classSession;
    }

    public void setClassSession(ClassSession classSession) {
        this.classSession = classSession;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentAttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(StudentAttendanceStatus studentAttendanceStatus) {
        this.status = studentAttendanceStatus;
    }

    public Tutor getMarkedBy() {
        return markedBy;
    }

    public void setMarkedBy(Tutor markedBy) {
        this.markedBy = markedBy;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
