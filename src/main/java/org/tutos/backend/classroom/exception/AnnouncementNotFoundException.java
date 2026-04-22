package org.tutos.backend.classroom.exception;

import java.util.UUID;

public class AnnouncementNotFoundException extends RuntimeException {
    public AnnouncementNotFoundException(UUID id) {
        super("Announcement with id: " + id + " was not found");
    }
}
