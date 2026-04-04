package org.lms.backend.user.exception;

public class StudentAlreadyParentChildException extends RuntimeException {
    public StudentAlreadyParentChildException(String message) {
        super(message);
    }
}
