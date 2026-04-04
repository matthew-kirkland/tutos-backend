package org.lms.backend.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User with id: " + id + " was not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
