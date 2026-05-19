package com.joboffersapi.domain.offersCRUD.exception;

public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException(final String s) {
        super(s);
    }
}
