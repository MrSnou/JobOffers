package com.joboffersapi.domain.offercrud;

import com.joboffersapi.domain.offercrud.dto.FetchedOffer;

import java.util.List;

public interface OfferFetchable {
    List<FetchedOffer> fetchOffers();
}
