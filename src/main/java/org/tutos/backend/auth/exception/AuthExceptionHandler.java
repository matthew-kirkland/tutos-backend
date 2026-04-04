package org.tutos.backend.auth.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.tutos.backend.common.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorMessageDto> handle(DuplicateUserException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDto(
                400,
                "DUPLICATE_USER",
                e.getMessage(),
                r.getRequestURI()
        ));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorMessageDto> handle(InvalidCredentialsException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDto(
                400,
                "INVALID_CREDENTIALS",
                e.getMessage(),
                r.getRequestURI()
        ));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorMessageDto> handle(TokenExpiredException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDto(
                401,
                "TOKEN_EXPIRED",
                e.getMessage(),
                r.getRequestURI()
        ));
    }
}
