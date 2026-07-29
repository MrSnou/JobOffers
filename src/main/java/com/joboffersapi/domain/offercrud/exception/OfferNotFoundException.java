package com.joboffersapi.domain.offercrud.exception;

public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException(final String s) {
        super(s);
    }
}
