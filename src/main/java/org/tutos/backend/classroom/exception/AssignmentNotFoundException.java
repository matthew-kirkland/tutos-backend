package org.tutos.backend.classroom.exception;

import java.util.UUID;

public class AssignmentNotFoundException extends RuntimeException {
    public AssignmentNotFoundException(UUID id) {
        super("Assignment with id: " + id + " was not found");
    }
}
