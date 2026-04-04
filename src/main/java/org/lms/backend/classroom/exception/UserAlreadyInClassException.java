package org.lms.backend.classroom.exception;

public class UserAlreadyInClassException extends RuntimeException {
    public UserAlreadyInClassException(String message) {
        super(message);
    }
}
