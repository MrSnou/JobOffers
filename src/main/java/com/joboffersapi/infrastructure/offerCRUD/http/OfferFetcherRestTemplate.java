package com.joboffersapi.infrastructure.offerCRUD.http;

import com.joboffersapi.domain.offerCRUD.OfferFetchable;
import com.joboffersapi.domain.offerCRUD.dto.FetchedOffer;
import com.joboffersapi.infrastructure.offerCRUD.http.dto.JobOfferFromApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
@Log4j2
public class OfferFetcherRestTemplate implements OfferFetchable {

    private final RestTemplate restTemplate;
    @Value("${job_offers.api.base-url}")
    private String baseUrl;

    @Override
    public List<FetchedOffer> fetchOffers() {
        log.info("RestTemplate: Fetching offers from external API ");
        String uri = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/offers")
                .toUriString();

        JobOfferFromApi[] body = null;
        try {
            body = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    JobOfferFromApi[].class
            ).getBody();
        } catch (ResponseStatusException e) {
            throw new RuntimeException("Error while fetching data from external server!\n" +
                    "Message: " + e.getMessage() + "\n" +
                    "Reason: " + e.getReason() + "\n"
                    ,e);
        }
        log.info("RestTemplate: Successfully fetched offers from external API, number of offers fetched: {}", body.length);

        return Arrays.stream(body)
                .map(JobOfferFromApi -> new FetchedOffer(
                        JobOfferFromApi.title(),
                        JobOfferFromApi.company(),
                        JobOfferFromApi.salary(),
                        JobOfferFromApi.offerUrl(),
                        JobOfferFromApi.source(),
                        JobOfferFromApi.salaryEstimated()
                ))
                .toList();
    }
}
