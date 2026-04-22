package org.tutos.backend.calendar.exception;

import java.util.UUID;

public class ClassSessionNotFoundException extends RuntimeException {
    public ClassSessionNotFoundException(UUID id) {
        super("Session with id " + id + " was not found");
    }
}
