package com.joboffersapi.domain.offersCRUD;

/*



 */

import com.joboffersapi.domain.offersCRUD.dto.AddOfferRequestDto;
import com.joboffersapi.domain.offersCRUD.dto.OfferDto;
import com.joboffersapi.domain.offersCRUD.dto.OfferResponseDto;
import com.joboffersapi.domain.offersCRUD.exception.OfferNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static com.joboffersapi.domain.offersCRUD.OfferFacadeConfiguration.getOfferFacadeForTests;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class OfferFacadeTest {


    OfferFacade offerFacade = getOfferFacadeForTests(
            new InMemoryOfferRepository(),
            new RestTemplateMock()
    );

    static class TestEntityFactory {

        private static final String DEFAULT_OFFER_NAME = "TestOffer";
        private static final String DEFAULT_OFFER_DESCRIPTION = "TestOffer";
        private static final Double DEFAULT_OFFER_SALARY = 67.67;
        private static final URL DEFAULT_OFFER_URL;

        static {
            try {
                DEFAULT_OFFER_URL = new URL("https://test.com");
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }

        static Offer anOffer() {
            return Offer.builder()
                    .title(DEFAULT_OFFER_NAME)
                    .description(DEFAULT_OFFER_DESCRIPTION)
                    .salary(DEFAULT_OFFER_SALARY)
                    .url(DEFAULT_OFFER_URL)
                    .build();
        }

        static AddOfferRequestDto anAddOfferRequestDto(Offer offer) {
            return AddOfferRequestDto.builder()
                    .title(offer.getTitle())
                    .description(offer.getDescription())
                    .salary(offer.getSalary())
                    .url(offer.getUrl())
                    .build();
        }

    }



    @Nested
    @DisplayName("addOffer - Tests")
    class AddOfferTests {
        @Test
        @DisplayName("Should return OfferResponseDto with message and offerDto.")
        public void should_return_OfferResponseDto_with_message_and_offerDto()  {
            // Given
                Offer offer = TestEntityFactory.anOffer();
                AddOfferRequestDto addOfferRequestDto = TestEntityFactory.anAddOfferRequestDto(offer);
            // When
                OfferResponseDto offerResponseDto = offerFacade.addOffer(addOfferRequestDto);
            // Then
                assertThat(offerResponseDto).isNotNull();
                assertThat(offerResponseDto.message()).isEqualTo("Successfully saved Offer : \n" +
                        "Title: " + offer.getTitle() + "\n" +
                        "Description: " + offer.getDescription() + "\n" +
                        "to database.");
                assertThat(offerResponseDto.offerDto()).isNotNull();
                assertThat(offerResponseDto.offerDto())
                        .extracting(OfferDto::title, OfferDto::description, OfferDto::salary, OfferDto::url)
                        .containsExactly(offer.getTitle(), offer.getDescription(), offer.getSalary(), offer.getUrl());
                assertThat(offerFacade.findAllOffers().size()).isEqualTo(1);
        }

    }

    @Nested
    @DisplayName("findOfferById - Tests")
    class FindOfferByIdTests {
        @Test
        @DisplayName("Should return OfferResponseDto with message and offerDto by id.")
        public void should_return_OfferResponseDto_with_message_and_offerDto_by_id() {
            // Given
            Offer offer = TestEntityFactory.anOffer();
            OfferResponseDto addedOffer = offerFacade.addOffer(TestEntityFactory.anAddOfferRequestDto(offer));
            // When
            OfferResponseDto offerResponseDto = offerFacade.findOfferById(addedOffer.offerDto().id());
            // Then
            assertThat(offerResponseDto).isNotNull();
            assertThat(offerResponseDto.message())
                    .isEqualTo("Offer with id " + addedOffer.offerDto().id() + " successfully found.");
            assertThat(offerResponseDto.offerDto()).isNotNull();
            assertThat(offerResponseDto.offerDto())
                    .extracting(OfferDto::title, OfferDto::description, OfferDto::salary, OfferDto::url)
                    .containsExactly(offer.getTitle(), offer.getDescription(), offer.getSalary(), offer.getUrl());

        }

        @Test
        @DisplayName("Should throw OfferNotFoundException when offer with given id does not exist.")
        public void should_throw_OfferNotFoundException_when_offer_with_given_id_does_not_exist() {
            // Given
            Long nonExistingId = 999L;
            // When
            Throwable throwable = catchThrowable(() -> offerFacade.findOfferById(nonExistingId));
            // Then
            assertThat(throwable).hasMessage("Offer with id " + nonExistingId + " not found.")
                    .isExactlyInstanceOf(OfferNotFoundException.class);

        }

        @Nested
        @DisplayName("findAllOffers - Tests")
        class FindAllOffersTest {
            @Test
            @DisplayName("Should return set of offers.")
            public void should_return_set_of_offers() {
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
            public void should_return_OfferResponseDto_with_message_and_null_offerDto() {
                // Given

                // When

                // Then

            }

        }


    }
}
