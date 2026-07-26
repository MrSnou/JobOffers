package com.joboffersapi.domain.offercrud;

import com.joboffersapi.domain.offercrud.dto.FetchedOffer;

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
