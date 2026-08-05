package com.joboffersapi.infrastructure.offercrud.http.util;

import com.joboffersapi.domain.offercrud.exception.InvalidOfferIdException;
import com.joboffersapi.domain.offercrud.exception.OfferNotFoundException;
import com.joboffersapi.infrastructure.offercrud.controller.JobOfferRestController;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;


@ControllerAdvice(basePackageClasses = JobOfferRestController.class)
@Log4j2
class ApiValidationErrorHandler {

    @ExceptionHandler(OfferNotFoundException.class)
    public ResponseEntity<ApiValidationErrorDto> handleOfferNotFound(RuntimeException ex) {
        log.error("[" + Instant.now() +  "] ApiValidationErrorHandler | handleOfferNotFound - " + ex.getMessage());
        ApiValidationErrorDto response = ApiValidationErrorDto.builder()
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InvalidOfferIdException.class)
    public ResponseEntity<ApiValidationErrorDto> handleInvalidOfferId(InvalidOfferIdException ex) {
        log.error("[" + Instant.now() +  "] ApiValidationErrorHandler | handleInvalidOfferId - " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiValidationErrorDto.builder()
                        .message(ex.getMessage())
                        .build());
    }
}
