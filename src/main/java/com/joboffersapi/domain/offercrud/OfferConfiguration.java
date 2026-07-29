package com.joboffersapi.domain.offercrud;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OfferConfiguration {

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    OfferFacade offerFacade(final OfferService offerService) {
        return new OfferFacade(offerService);
    }
}
