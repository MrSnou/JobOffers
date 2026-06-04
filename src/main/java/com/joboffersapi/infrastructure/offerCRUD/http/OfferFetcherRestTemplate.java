package com.joboffersapi.infrastructure.offerCRUD.http;

import com.joboffersapi.domain.offerCRUD.OfferFetchable;
import com.joboffersapi.domain.offerCRUD.dto.FetchedOffer;
import com.joboffersapi.infrastructure.offerCRUD.http.dto.JobOfferFromApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
public class OfferFetcherRestTemplate implements OfferFetchable {

    private final RestTemplate restTemplate;
    @Value("${job_offers.api.base-url}")
    private String baseUrl;

    @Override
    public List<FetchedOffer> fetchOffers() {
        String uri = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/offers")
                .toUriString();

        JobOfferFromApi[] body = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                JobOfferFromApi[].class
        ).getBody();

        return Arrays.stream(body)
                .map(InfrastructureOfferMapper::mapFromJobOfferFromApiToFetchedOffer)
                .toList();
    }
}
