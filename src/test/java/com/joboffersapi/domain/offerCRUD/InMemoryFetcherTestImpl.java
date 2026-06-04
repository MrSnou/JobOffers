package com.joboffersapi.domain.offerCRUD;

import com.joboffersapi.domain.offerCRUD.dto.FetchedOffer;

import java.util.List;

class InMemoryFetcherTestImpl implements OfferFetchable{

    String jsonString;

    InMemoryFetcherTestImpl(final String jsonString) {
        this.jsonString = jsonString;
    }

    @Override
    public List<FetchedOffer> fetchOffers() {
        return OfferMapper.mapFromJsonToOffers(jsonString);
    }
}
