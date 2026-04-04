package org.lms.backend.classroom.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long assignmentId;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class classBelongingTo;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private LocalDateTime timePosted;

    public Assignment(
            Class classBelongingTo,
            String title,
            String description,
            LocalDateTime dueDate
    ) {
        this.setClassBelongingTo(classBelongingTo);
        this.setTitle(title);
        this.setDescription(description);
        this.setDueDate(dueDate);
        this.timePosted = LocalDateTime.now();
    }

    protected Assignment() {
    }

    public void updateAssignmentDetails(String title, String description, LocalDateTime dueDate) {
        this.setTitle(title);
        this.setDescription(description);
        this.setDueDate(dueDate);
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public Class getClassBelongingTo() {
        return classBelongingTo;
    }

    public void setClassBelongingTo(Class classBelongingTo) {
        this.classBelongingTo = classBelongingTo;
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

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getTimePosted() {
        return timePosted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Assignment other)) return false;
        return assignmentId != null && assignmentId.equals(other.getAssignmentId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
