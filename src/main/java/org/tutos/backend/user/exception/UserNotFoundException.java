package org.tutos.backend.user.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID id) {
        super("User with id: " + id + " was not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
