package com.joboffersapi.domain.offersCRUD;

/*



 */

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.joboffersapi.domain.offersCRUD.OfferFacadeConfiguration.getOfferFacadeForTests;

class OfferFacadeTest {


    OfferFacade offerFacade = getOfferFacadeForTests(
            new InMemoryOfferRepository(),
            new RestTemplateMock()
    );



    @Nested
    @DisplayName("addOffer - Tests")
    class AddOfferTests {
        @Test
        @DisplayName("Should return OfferResponseDto with message and offerDto.")
        public void should_return_set_of_offers_when_user_called_for_method() {
            // Given

            // When

            // Then

        }

    }

    @Nested
    @DisplayName("findOfferById - Tests")
    class FindOfferByIdTests {
        @Test
        @DisplayName("Should return OfferResponseDto with message and offerDto.")
        public void should_return_set_of_offers_when_user_called_for_method() {
            // Given

            // When

            // Then

        }

    }

    @Nested
    @DisplayName("findAllOffers - Tests")
    class FindAllOffersTest {
        @Test
        @DisplayName("Should return set of offers.")
        public void should_return_set_of_offers_when_user_called_for_method() {
            // Given

            // When

            // Then

        }

    }

    @Nested
    @DisplayName("fetchAllOffersAndSaveIfNotExists - Tests")
    class FetchAllOffersAndSaveIfNotExistsTests {
        @Test
        @DisplayName("Should return OfferResponseDto with message and null offerDto.")
        public void should_return_set_of_offers_when_user_called_for_method() {
            // Given

            // When

            // Then

        }

    }



}
