package com.joboffersapi.infrastructure.usercrud.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class LoginAndRegisterControllerErrorHandler {

//    TODO : Handlers for @Validation errors.

    private static final String BAD_CREDENTIALS = "Bad credentials.";

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<LoginAndRegisterErrorResponse> handleBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginAndRegisterErrorResponse(BAD_CREDENTIALS));
    }
}
