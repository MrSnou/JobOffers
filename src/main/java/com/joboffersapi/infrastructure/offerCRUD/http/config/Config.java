package com.joboffersapi.infrastructure.offerCRUD.http.config;

import com.joboffersapi.domain.offerCRUD.OfferFetchable;
import com.joboffersapi.infrastructure.offerCRUD.http.OfferFetcherRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class Config {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(restTemplateResponseErrorHandler);
        return restTemplate;
    }

    @Bean
    public OfferFetchable offerFetchable(RestTemplate restTemplate) {
        return new OfferFetcherRestTemplate(restTemplate);
    }
}
