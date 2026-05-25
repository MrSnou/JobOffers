package com.joboffersapi.domain.offerCRUD;

import com.joboffersapi.domain.offerCRUD.dto.JobOfferResponse;

import java.util.List;

interface OfferFetchable {
    List<JobOfferResponse> fetchOffers();
}
