package org.tutos.backend.classroom.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long announcementId;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class classBelongingTo;
    private String title;
    private String message;
    private LocalDateTime timePosted;

    public Announcement(
            Class classBelongingTo,
            String title,
            String message
    ) {
        this.setClassBelongingTo(classBelongingTo);
        this.setTitle(title);
        this.setMessage(message);
        this.timePosted = LocalDateTime.now();
    }

    protected Announcement() {
    }

    public void updateAnnouncementDetails(
            String title,
            String message
    ) {
        this.setTitle(title);
        this.setMessage(message);
    }

    public Long getAnnouncementId() {
        return announcementId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimePosted() {
        return timePosted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Announcement other)) return false;
        return announcementId != null && announcementId.equals(other.getAnnouncementId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
