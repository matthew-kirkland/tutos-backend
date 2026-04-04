package org.lms.backend.calendar.exception;

public class ClassSessionNotFoundException extends RuntimeException {
    public ClassSessionNotFoundException(Long id) {
        super("Session with id " + id + " was not found");
    }
}
