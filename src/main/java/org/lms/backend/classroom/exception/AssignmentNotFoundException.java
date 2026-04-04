package org.lms.backend.classroom.exception;

public class AssignmentNotFoundException extends RuntimeException {
    public AssignmentNotFoundException(Long id) {
        super("Assignment with id: " + id + " was not found");
    }
}
