package com.joboffersapi.infrastructure.offercrud.http.config;

import com.joboffersapi.domain.offercrud.OfferFetchable;
import com.joboffersapi.infrastructure.offercrud.http.OfferFetcherRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateClientConfig {

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
