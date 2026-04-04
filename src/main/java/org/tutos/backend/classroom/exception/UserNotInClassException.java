package org.tutos.backend.classroom.exception;

public class UserNotInClassException extends RuntimeException {
    public UserNotInClassException(String message) {
        super(message);
    }
}
