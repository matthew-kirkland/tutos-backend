package org.lms.backend.calendar.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.lms.backend.common.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CalendarExceptionHandler {
    @ExceptionHandler(ClassSessionNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handle(ClassSessionNotFoundException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageDto(
                404,
                "SESSION_NOT_FOUND",
                e.getMessage(),
                r.getRequestURI()
        ));
    }
}
