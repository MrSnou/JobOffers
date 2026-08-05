package com.joboffersapi.domain.offercrud.exception;

public class InvalidOfferIdException extends RuntimeException {
    public InvalidOfferIdException() {
        super("Invalid offer id. Expected format: 24 characters, digits and letters a–f, e.g. 6a6a386a6a7fad2d161c487e");
    }
}
