package org.tutos.backend.classroom.exception;

import java.util.UUID;

public class ClassNotFoundException extends RuntimeException {
    public ClassNotFoundException(UUID id) {
        super("Class with id: " + id + " was not found");
    }
}
