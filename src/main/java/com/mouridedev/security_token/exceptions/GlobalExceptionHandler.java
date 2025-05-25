package com.mouridedev.security_token.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SecurityTokenException.class)
    public ResponseEntity<ErrorApiDTO> handleSecurityException(final SecurityTokenException exception) {
        return buildResponseException(exception);
    }

    private ResponseEntity<ErrorApiDTO> buildResponseException(final SecurityTokenException exception) {
        final var error = new ErrorApiDTO(
                exception.getCodeErreur(),
                exception.getMessage()
        );

        log.error("SecurityTokenException thrown - httpCode: {}, ApiError: {}", exception.getStatutHttp(), error);
        return new ResponseEntity<>(error, HttpStatus.valueOf(exception.getStatutHttp()));
    }
}
