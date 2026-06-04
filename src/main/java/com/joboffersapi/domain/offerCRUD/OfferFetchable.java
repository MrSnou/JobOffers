package com.joboffersapi.domain.offerCRUD;

import com.joboffersapi.domain.offerCRUD.dto.FetchedOffer;

import java.util.List;

public interface OfferFetchable {
    List<FetchedOffer> fetchOffers();
}
