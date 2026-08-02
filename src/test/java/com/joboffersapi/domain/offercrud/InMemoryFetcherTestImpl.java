package com.joboffersapi.domain.offercrud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joboffersapi.domain.offercrud.dto.FetchedOffer;

import java.util.List;

class InMemoryFetcherTestImpl implements OfferFetchable{

    String jsonString;
    ObjectMapper objectMapper =  new ObjectMapper();

    InMemoryFetcherTestImpl(final String jsonString) {
        this.jsonString = jsonString;
    }

    @Override
    public List<FetchedOffer> fetchOffers() {
        return mapFromJsonToOffers(jsonString);
    }

    List<FetchedOffer> mapFromJsonToOffers(String json) {
        try {
            return objectMapper.readValue(
                    json,
                    new TypeReference<List<FetchedOffer>>() {
                    }
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while mapping JSON to Offers: " + e.getMessage());
        }
    }
}
