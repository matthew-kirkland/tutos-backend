package org.tutos.backend.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.tutos.backend.common.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(InvalidUserTypeException.class)
    public ResponseEntity<ErrorMessageDto> handle(InvalidUserTypeException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessageDto(
                403,
                "INVALID_USER_TYPE",
                e.getMessage(),
                r.getRequestURI()
        ));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorMessageDto> handle(ValidationException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDto(
                400,
                "INVALID_INPUT",
                e.getMessage(),
                r.getRequestURI()
        ));
    }

    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<ErrorMessageDto> handle(ForbiddenOperationException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessageDto(
                403,
                "FORBIDDEN_OPERATION",
                e.getMessage(),
                r.getRequestURI()
        ));
    }
}
