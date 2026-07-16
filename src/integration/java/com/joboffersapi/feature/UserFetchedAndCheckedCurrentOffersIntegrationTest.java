package com.joboffersapi.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.joboffersapi.BaseIntegrationTest;
import com.joboffersapi.domain.offerCRUD.OfferFacade;
import com.joboffersapi.domain.offerCRUD.OfferFetchable;
import com.joboffersapi.domain.offerCRUD.dto.FetchOfferResponseDto;
import com.joboffersapi.domain.offerCRUD.dto.FetchedOffer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


class UserFetchedAndCheckedCurrentOffersIntegrationTest extends BaseIntegrationTest {


    @Autowired
    OfferFetchable offerFetchable;
    @Autowired
    OfferFacade offerFacade;

    @Test
    @DisplayName("User fetched and checked current offers - Happy Path Test")
    void HappyPath() {
        // Given
        wireMockExtension.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [
                                {"title":"Junior Java",
                                "company":"X",
                                "salary":"5000 PLN",
                                "offerUrl":"http://x.pl",
                                "source":"X",
                                "salary_estimated":false}
                                ,
                                {"title":"Mid Java",
                                "company":"Y",
                                "salary":"10000 PLN",
                                "offerUrl":"http://y.pl",
                                "source":"Y",
                                "salary_estimated":true}
                                ]
                                """.trim())));
        // When
        Iterable<FetchedOffer> jobOfferFromApis = offerFetchable.fetchOffers();
        // Then
        assertThat(jobOfferFromApis).hasSize(2);
        assertThat(jobOfferFromApis).containsExactlyInAnyOrder(
                new FetchedOffer("Junior Java", "X", "5000 PLN",
                        "http://x.pl", "X", false),
                new FetchedOffer("Mid Java", "Y", "10000 PLN",
                        "http://y.pl", "Y", true)
        );
        // 1. User sending request to controller

        // 2. Service layer fetching offers from external site and saving them to db if not exists
        // 3. Service layer fetching offers from repository
        // 4. Service layer returning offers list to controller
        // 5. Controller returns data to user
    }
}
