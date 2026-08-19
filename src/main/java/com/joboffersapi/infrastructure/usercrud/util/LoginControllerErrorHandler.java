package com.joboffersapi.infrastructure.usercrud.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class LoginControllerErrorHandler {

    private static final String BAD_CREDENTIALS = "Bad credentials.";

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<LoginErrorResponse> handleBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginErrorResponse(BAD_CREDENTIALS));
    }
}
