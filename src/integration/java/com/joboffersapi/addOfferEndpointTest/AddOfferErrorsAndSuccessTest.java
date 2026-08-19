package com.joboffersapi.addOfferEndpointTest;

import com.joboffersapi.BaseIntegrationTest;
import com.joboffersapi.domain.offercrud.dto.OfferDto;
import com.joboffersapi.domain.offercrud.dto.OfferResponseDto;
import com.joboffersapi.domain.offercrud.dto.OffersListDto;
import com.joboffersapi.infrastructure.offercrud.http.dto.AddOfferRequest;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AddOfferErrorsAndSuccessTest extends BaseIntegrationTest {

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    public void testAddOfferErrorsAndSuccess() throws Exception {
        /// 1. User after login trying to fetch for all offers from API, should return 0 offers.
        // Given && When
        ResultActions firstTimeEmptyFetch = mockMvc.perform(get("/offers").accept(MediaType.APPLICATION_JSON));
        // Then
        firstTimeEmptyFetch.andExpect(status().isOk());
        OffersListDto offersEndpointResponseBody = objectMapper.readValue
                (firstTimeEmptyFetch.andReturn().getResponse().getContentAsString(), OffersListDto.class);
        assertThat(offersEndpointResponseBody.offers().size()).isEqualTo(0);

        /// 2. User adding offer to database by direct endpoint POST /offers, should return [201] - Created with confirmation message and OfferDto
        // given
        AddOfferRequest CorrectDataTestOffer = AddOfferRequest.builder()
                .title("Test Title")
                .company("Test Company")
                .salaryMin(20.0)
                .salaryMax(25.0)
                .url("https://www.test.com")
                .source("https://www.test.com")
                .salary_estimated(false)
                .build();
        // When
        ResultActions postOfferRequest = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CorrectDataTestOffer)));
        // Then
        postOfferRequest.andExpect(status().isCreated());
        OfferResponseDto offerResponseDto = objectMapper
                .readValue(postOfferRequest.andReturn().getResponse().getContentAsString(), OfferResponseDto.class);
        assertThat(offerResponseDto.message()).isEqualTo("Successfully saved Offer : " +
                "Title: " + CorrectDataTestOffer.title() +
                " | Company: " + CorrectDataTestOffer.company() +
                " to database.");
        String salaryAfterMapping = String.format("%.0f - %.0f PLN", CorrectDataTestOffer.salaryMin(), CorrectDataTestOffer.salaryMax());
        assertThat(offerResponseDto.offerDto()).extracting(OfferDto::title, OfferDto::company, OfferDto::salary, OfferDto::url, OfferDto::source)
                .containsExactly(CorrectDataTestOffer.title(), CorrectDataTestOffer.company(), salaryAfterMapping , CorrectDataTestOffer.url(), CorrectDataTestOffer.source());
        /// 3. User trying to add duplicate offer to database by direct endpoint post /offers.
        // Given && When (User sends exactly the same request again)
        ResultActions duplicatePostOfferRequest = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CorrectDataTestOffer)));
        // Then
        duplicatePostOfferRequest.andExpectAll(
                status().isConflict(),
                result -> assertThat(result.getResolvedException()).isInstanceOf(DuplicateKeyException.class),
                jsonPath("$.errors", hasSize(1)),
                jsonPath("$.errors", contains("Offer with this URL already exists!"))
        );

        /// 4. User adding offer to database by post /offers with empty body, should return all errors in list.
        // Given
        AddOfferRequest emptyDataObject = AddOfferRequest.builder()
                .title(null)
                .company(null)
                .salaryMin(null)
                .salaryMax(null)
                .url(null)
                .source(null)
                .salary_estimated(null)
                .build();
        // When
        ResultActions emptyPostOfferRequest = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(emptyDataObject)));
        // Then
        emptyPostOfferRequest.andExpectAll(
                status().isBadRequest(),
                result -> assertThat(result.getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class),
                jsonPath("$.errors", hasSize(7)),
                jsonPath("$.errors", containsInAnyOrder(
                            "Offer maximum salary have to contain at least 1 character.",
                            "Offer minimal salary have to contain at least 1 character.",
                            "Source of offer have to contain at least 1 character.",
                            "Offer url have to contain at least 1 character.",
                            "Offer company have to contain at least 1 character.",
                            "Offer title cannot be empty.",
                            "Estimated salary cannot be null."
                    )));
    }
}
