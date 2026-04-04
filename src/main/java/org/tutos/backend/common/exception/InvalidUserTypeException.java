package org.tutos.backend.common.exception;

public class InvalidUserTypeException extends RuntimeException {
    public InvalidUserTypeException(String message) {
        super(message);
    }
}
