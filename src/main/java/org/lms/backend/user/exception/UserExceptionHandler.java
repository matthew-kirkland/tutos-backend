package org.lms.backend.user.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.lms.backend.common.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handle(UserNotFoundException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageDto(
                404,
                "USER_NOT_FOUND",
                e.getMessage(),
                r.getRequestURI()
        ));
    }

    @ExceptionHandler(StudentAlreadyParentChildException.class)
    public ResponseEntity<ErrorMessageDto> handle(StudentAlreadyParentChildException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessageDto(
                403,
                "STUDENT_ALREADY_PARENT_CHILD",
                e.getMessage(),
                r.getRequestURI()
        ));
    }
}

