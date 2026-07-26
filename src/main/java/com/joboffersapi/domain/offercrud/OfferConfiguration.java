package com.joboffersapi.domain.offercrud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OfferConfiguration {

    @Bean
    OfferFacade offerFacade(final OfferService offerService) {
        return new OfferFacade(offerService);
    }
}
