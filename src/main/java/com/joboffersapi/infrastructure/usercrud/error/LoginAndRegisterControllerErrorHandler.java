package com.joboffersapi.infrastructure.usercrud.error;

import com.joboffersapi.domain.usercrud.exception.UserExistsException;
import com.joboffersapi.infrastructure.usercrud.LoginAndRegisterController;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice(basePackageClasses = LoginAndRegisterController.class)
@Log4j2
class LoginAndRegisterControllerErrorHandler {

    private static final String BAD_CREDENTIALS = "Bad credentials.";

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public LoginAndRegisterErrorResponse handleBadCredentials(BadCredentialsException ex) {
        final List<String> errors = List.of("Invalid username or password.");
        log.warn("[" + Instant.now() + "] handleBadCredentials - authentication failed");
        return LoginAndRegisterErrorResponse.builder()
                .errors(errors).build();
    }

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public LoginAndRegisterErrorResponse handleUserExistsException(UserExistsException ex) {
        final List<String> errors = List.of("User already exists.");
        log.warn("[" + Instant.now() + "] handleUserExistsException - user already exists");
        return LoginAndRegisterErrorResponse.builder()
                .errors(errors).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public LoginAndRegisterErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        final List<String> errors = getErrorsFromException(ex);
        log.warn("[" + Instant.now() + "] handleMethodArgumentNotValidException - validation failed");
        return LoginAndRegisterErrorResponse.builder()
                .errors(errors).build();
    }

    private List<String> getErrorsFromException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }

}
