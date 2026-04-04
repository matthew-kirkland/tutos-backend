package org.lms.backend.classroom.exception;

public class AssignmentNotInClassException extends RuntimeException {
    public AssignmentNotInClassException(String message) {
        super(message);
    }
}
