package com.joboffersapi.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.joboffersapi.BaseIntegrationTest;
import com.joboffersapi.domain.offercrud.dto.OfferDto;
import com.joboffersapi.domain.offercrud.dto.OfferResponseDto;
import com.joboffersapi.domain.offercrud.dto.OffersListDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserFetchedAndCheckedCurrentOffersIntegrationTest extends BaseIntegrationTest {
    // TODO : Scheduler fetch mid test with new offers.

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    @DisplayName("User fetched and checked current offers - Happy Path Test")
    void HappyPath() throws Exception {
        /// 1. User after login (Not Implemented yet) trying to fetch for all offers from API, should return 0 offers.
        // Given && When
        ResultActions firstTimeEmptyFetch = mockMvc.perform(get("/offers").accept(MediaType.APPLICATION_JSON));
        // Then
        firstTimeEmptyFetch.andExpect(status().isOk());
        OffersListDto offersEndpointResponseBody = objectMapper.readValue
                (firstTimeEmptyFetch.andReturn().getResponse().getContentAsString(), OffersListDto.class);
        assertThat(offersEndpointResponseBody.offers().size()).isEqualTo(0);
        /// 2. User calls for refreshAndGetOffers to fetch for the freshest offers and add them to db.
        // Given
        wireMockExtension.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("""
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
        ResultActions refreshedOffersResponse = mockMvc.perform(get("/offers/refreshAndGetOffers").accept(MediaType.APPLICATION_JSON));
        // Then
        refreshedOffersResponse.andExpect(status().isOk());
        OffersListDto offersStateAfterRefreshResponse = objectMapper.readValue(refreshedOffersResponse.andReturn().getResponse().getContentAsString(), OffersListDto.class);
        assertThat(offersStateAfterRefreshResponse.offers().size()).isEqualTo(2);
        String firstOfferId = offersStateAfterRefreshResponse.offers().get(0).id();
        String secondOfferId = offersStateAfterRefreshResponse.offers().get(1).id();
        assertThat(offersStateAfterRefreshResponse.offers()).containsExactlyInAnyOrder(
                new OfferDto(
                        firstOfferId,
                        "Junior Java",
                        "X",
                        "5000 PLN",
                        "http://x.pl",
                        "X"),
                new OfferDto(
                        secondOfferId,
                        "Mid Java",
                        "Y",
                        "10000 PLN",
                        "http://y.pl",
                        "Y"));

        /// 3. User made get request with incorrect entity HEX-String ID,
        /// then application returned HttpStatus 400, wrong ID (InvalidOfferIdException.class).

        // Given
        ResultActions performGetWithWrongData = mockMvc.perform(get("/offers/123"));
        // When && Then
        performGetWithWrongData.andExpect(status().isBadRequest()).andExpect(content().json("""
                {
                "errors":["Invalid offer id. Expected format: 24 characters, digits and letters a–f, e.g. 6a6a386a6a7fad2d161c487e"]
                }
                """.trim()));


        /// 4. User made GET request with correct 24-letters/digits to /offers endpoint, but there is no entity in DB with this ID,
        /// then application returned HttpStatus 404, offer not found.
        // Given
        ResultActions performGetNotExistingOffer = mockMvc.perform(get("/offers/123456789012345678901234"));
        // When && Then
        performGetNotExistingOffer.andExpect(status().isNotFound()).andExpect(content().json("""
                {
                  "errors":["Offer with id 123456789012345678901234 not found."]
                }
                """.trim()));

        /// 5. User made GET request with correct offer ID, application returned status 200 with OfferDto object.
        // Given && When
        ResultActions getWithIdResponse = mockMvc.perform(get("/offers/" + firstOfferId)
                .accept(MediaType.APPLICATION_JSON));
        // Then
        String contentAsString = getWithIdResponse.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        OfferResponseDto returnedOfferDto = objectMapper.readValue(contentAsString, OfferResponseDto.class);
        assertThat(returnedOfferDto).isNotNull();
        assertThat(returnedOfferDto.message()).isEqualTo(String.format("Offer with id %s successfully found.", firstOfferId));
        assertThat(returnedOfferDto.offerDto()).isEqualTo(new OfferDto(
                firstOfferId,
                "Junior Java",
                "X",
                "5000 PLN",
                "http://x.pl",
                "X"));
    }
}
