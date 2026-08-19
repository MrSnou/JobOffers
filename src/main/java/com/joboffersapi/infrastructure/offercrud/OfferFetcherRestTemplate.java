package com.joboffersapi.infrastructure.offercrud;

import com.joboffersapi.domain.offercrud.OfferFetchable;
import com.joboffersapi.domain.offercrud.dto.FetchedOffer;
import com.joboffersapi.domain.offercrud.exception.OfferFetchingException;
import com.joboffersapi.infrastructure.offercrud.dto.JobOfferFromApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
@Log4j2
public class OfferFetcherRestTemplate implements OfferFetchable {

    final private RestTemplate restTemplate;
    final private String baseUrl;

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
        } catch (RestClientException e) {
            log.error("Failed to fetch offers from external API: {}", e.getMessage());
            throw new OfferFetchingException("Could not fetch offers from external API.", e);
        }
        if (body == null) {
            log.warn("RestTemplate: External API returned empty body.");
            return List.of();
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
