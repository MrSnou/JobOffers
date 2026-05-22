package com.joboffersapi.domain.offersCRUD;

import org.springframework.web.client.RestTemplate;

class OfferFacadeConfiguration {

    public static  OfferFacade getOfferFacadeForTests(OfferRepository offerRepository, RestTemplate restTemplate) {
        OfferService offerAdder = new OfferService(offerRepository);
        OfferFetcher offerFetcher = new OfferFetcher(restTemplate, offerRepository);
        return new OfferFacade( offerAdder, offerFetcher);
    }
}
