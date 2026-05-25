package com.joboffersapi.domain.offerCRUD.exception;

public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException(final String s) {
        super(s);
    }
}
