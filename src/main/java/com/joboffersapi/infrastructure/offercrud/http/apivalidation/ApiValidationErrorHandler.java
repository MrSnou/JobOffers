package com.joboffersapi.infrastructure.offercrud.http.apivalidation;

import com.joboffersapi.domain.offercrud.exception.InvalidOfferIdException;
import com.joboffersapi.domain.offercrud.exception.OfferFetchingException;
import com.joboffersapi.domain.offercrud.exception.OfferNotFoundException;
import com.joboffersapi.infrastructure.offercrud.http.controller.JobOfferRestController;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;


@RestControllerAdvice(basePackageClasses = JobOfferRestController.class)
@Log4j2
class ApiValidationErrorHandler {

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiValidationErrorDto handleOfferNotFound(OfferNotFoundException ex) {
        final List<String> errors = List.of(ex.getMessage());
        log.error("[" + Instant.now() +  "] ApiValidationErrorHandler | handleOfferNotFound - " + errors);
        return ApiValidationErrorDto.builder()
                .errors(errors)
                .build();
    }

    @ExceptionHandler(InvalidOfferIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiValidationErrorDto handleInvalidOfferId(InvalidOfferIdException ex) {
        final List<String> errors = List.of(ex.getMessage());
        log.error("[" + Instant.now() +  "] ApiValidationErrorHandler | handleInvalidOfferId - " + errors);
        return ApiValidationErrorDto.builder()
                        .errors(errors)
                        .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiValidationErrorDto handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        final List<String> errors = List.of("Request body is malformed or contains invalid value types.");
        log.error("[" + Instant.now() +  "] ApiValidationErrorHandler | handleHttpMessageNotReadable - " + errors);
        return ApiValidationErrorDto.builder()
                    .errors(errors)
                    .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiValidationErrorDto handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        final List<String> errors = getErrorsFromException(ex);
        log.error("[" + Instant.now() +  "] ApiValidationErrorHandler | handleMethodArgumentNotValid - " + errors);
        return new ApiValidationErrorDto(errors);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiValidationErrorDto handleDuplicateKeyException(DuplicateKeyException ex) {
        final List<String> errors = List.of("Offer with this URL already exists!");
        log.warn("[" +  Instant.now() +  "] ApiValidationErrorHandler | handleDuplicateKey - " + errors + " | " + ex.getMessage());
        return new ApiValidationErrorDto(errors);
    }

    @ExceptionHandler(OfferFetchingException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ApiValidationErrorDto handleOfferFetching(OfferFetchingException ex) {
        final List<String> errors = List.of("External offers API is currently unavailable.");
        log.error("[" + Instant.now() + "] ApiValidationErrorHandler | handleOfferFetching - " + ex.getMessage());
        return ApiValidationErrorDto.builder().errors(errors).build();
    }



    private List<String> getErrorsFromException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }
}
