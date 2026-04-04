package org.lms.backend.classroom.exception;

public class ClassNotFoundException extends RuntimeException {
    public ClassNotFoundException(Long id) {
        super("Class with id: " + id + " was not found");
    }
}
