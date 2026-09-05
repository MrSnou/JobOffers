package com.joboffersapi.infrastructure.offercrud.config;

import com.joboffersapi.domain.offercrud.OfferFetchable;
import com.joboffersapi.infrastructure.offercrud.OfferFetcherRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;


@Configuration
public class RestTemplateClientConfig {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler errorHandler,
                                     @Value("${job-offers.api.connection-timeout}") long connectionTimeout,
                                     @Value("${job-offers.api.read-timeout}") long readTimeout) {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        return builder
                .connectTimeout(Duration.ofMillis(connectionTimeout))
                .readTimeout(Duration.ofMillis(readTimeout))
                .errorHandler(errorHandler)
                .build();
    }

    @Bean
    public OfferFetchable offerFetchable(RestTemplate restTemplate,
                                         @Value("${job-offers.api.base-url}") String baseUrl) {
        return new OfferFetcherRestTemplate(restTemplate, baseUrl);
    }
}
