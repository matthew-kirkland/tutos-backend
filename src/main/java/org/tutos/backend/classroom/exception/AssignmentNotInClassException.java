package org.tutos.backend.classroom.exception;

public class AssignmentNotInClassException extends RuntimeException {
    public AssignmentNotInClassException(String message) {
        super(message);
    }
}
