package org.tutos.backend.user.exception;

public class StudentAlreadyParentChildException extends RuntimeException {
    public StudentAlreadyParentChildException(String message) {
        super(message);
    }
}
