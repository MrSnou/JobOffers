package com.joboffersapi.domain.offerCRUD;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JobOfferConfiguration {

    @Bean
    OfferFacade offerFacade(final OfferService offerService) {
        return new OfferFacade(offerService);
    }
}
