package org.tutos.backend.classroom.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.tutos.backend.common.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClassroomExceptionHandler {
    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handle(ClassNotFoundException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageDto(
                404,
                "CLASS_NOT_FOUND",
                e.getMessage(),
                r.getRequestURI()
        ));
    }

    @ExceptionHandler(AssignmentNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handle(AssignmentNotFoundException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageDto(
                404,
                "ASSIGNMENT_NOT_FOUND",
                e.getMessage(),
                r.getRequestURI()
        ));
    }

    @ExceptionHandler(AnnouncementNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handle(AnnouncementNotFoundException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageDto(
                404,
                "ANNOUNCEMENT_NOT_FOUND",
                e.getMessage(),
                r.getRequestURI()
        ));
    }

    @ExceptionHandler(UserNotInClassException.class)
    public ResponseEntity<ErrorMessageDto> handle(UserNotInClassException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageDto(
                403,
                "USER_NOT_IN_CLASS",
                e.getMessage(),
                r.getRequestURI()
        ));
    }

    @ExceptionHandler(AssignmentNotInClassException.class)
    public ResponseEntity<ErrorMessageDto> handle(AssignmentNotInClassException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageDto(
                403,
                "ASSIGNMENT_NOT_IN_CLASS",
                e.getMessage(),
                r.getRequestURI()
        ));
    }

    @ExceptionHandler(AnnouncementNotInClassException.class)
    public ResponseEntity<ErrorMessageDto> handle(AnnouncementNotInClassException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageDto(
                403,
                "ANNOUNCEMENT_NOT_IN_CLASS",
                e.getMessage(),
                r.getRequestURI()
        ));
    }

    @ExceptionHandler(ClassFullException.class)
    public ResponseEntity<ErrorMessageDto> handle(ClassFullException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessageDto(
                403,
                "CLASS_FULL",
                e.getMessage(),
                r.getRequestURI()
        ));
    }

    @ExceptionHandler(UserAlreadyInClassException.class)
    public ResponseEntity<ErrorMessageDto> handle(UserAlreadyInClassException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessageDto(
                403,
                "USER_ALREADY_IN_CLASS",
                e.getMessage(),
                r.getRequestURI()
        ));
    }
}
