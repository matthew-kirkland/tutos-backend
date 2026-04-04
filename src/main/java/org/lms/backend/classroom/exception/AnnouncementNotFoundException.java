package org.lms.backend.classroom.exception;

public class AnnouncementNotFoundException extends RuntimeException {
    public AnnouncementNotFoundException(Long id) {
        super("Announcement with id: " + id + " was not found");
    }
}
