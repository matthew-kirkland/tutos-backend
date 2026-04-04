package org.lms.backend.classroom.exception;

public class ClassFullException extends RuntimeException {
    public ClassFullException(String message) {
        super(message);
    }
}
