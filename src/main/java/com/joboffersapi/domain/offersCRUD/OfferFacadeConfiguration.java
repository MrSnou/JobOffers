package com.joboffersapi.domain.offersCRUD;

import org.springframework.web.client.RestTemplate;

class OfferFacadeConfiguration {

    public static  OfferFacade getOfferFacadeForTests(OfferRepository offerRepository, RestTemplate restTemplate) {
        OfferReceiver offerService = new OfferReceiver(offerRepository);
        OfferAdder offerAdder = new OfferAdder(offerRepository);
        OfferFetcher offerFetcher = new OfferFetcher(restTemplate, offerRepository);
        return new OfferFacade(offerService, offerAdder, offerFetcher);
    }
}
