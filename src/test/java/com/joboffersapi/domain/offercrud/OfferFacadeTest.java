package com.joboffersapi.domain.offercrud;

import com.joboffersapi.domain.offercrud.dto.FetchOfferResponseDto;
import com.joboffersapi.domain.offercrud.dto.OfferDto;
import com.joboffersapi.domain.offercrud.dto.OfferResponseDto;
import com.joboffersapi.domain.offercrud.exception.InvalidOfferIdException;
import com.joboffersapi.domain.offercrud.exception.OfferNotFoundException;
import com.joboffersapi.infrastructure.offercrud.dto.AddOfferRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.joboffersapi.infrastructure.offercrud.util.HttpLayerOfferMapper.mapFromAddOfferRequestToAddOfferRequestDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class OfferFacadeTest {

    // TODO : AddOffer tests.

    private final OfferFacadeTestConfiguration offerFacadeTestConfiguration = new OfferFacadeTestConfiguration();

    OfferFacade offerFacade = offerFacadeTestConfiguration.getOfferFacadeForTests();

    @BeforeEach
    void setUp() {
        offerFacadeTestConfiguration.clearDatabase();
    }

    static class TestEntityFactory {

        private static final String DEFAULT_OFFER_NAME = "TestOffer";
        private static final String DEFAULT_OFFER_COMPANY = "TestCompany";
        private static final String DEFAULT_OFFER_SALARY = "67.67";
        private static final String DEFAULT_OFFER_URL = "http://localhost:8080";
        private static final String DEFAULT_SOURCE = "LinkedIn";
        private static final boolean DEFAULT_SALARY_ESTIMATED = false;

        static Offer anOffer() {
            return Offer.builder()
                    .title(DEFAULT_OFFER_NAME)
                    .company(DEFAULT_OFFER_COMPANY)
                    .salary(DEFAULT_OFFER_SALARY)
                    .url(DEFAULT_OFFER_URL)
                    .source(DEFAULT_SOURCE)
                    .salary_estimated(DEFAULT_SALARY_ESTIMATED)
                    .build();
        }

        static Offer anOffer(String name) {
            return Offer.builder()
                    .title(name)
                    .company(DEFAULT_OFFER_COMPANY)
                    .salary(DEFAULT_OFFER_SALARY)
                    .url(DEFAULT_OFFER_URL)
                    .source(DEFAULT_SOURCE)
                    .salary_estimated(DEFAULT_SALARY_ESTIMATED)
                    .build();
        }

        static AddOfferRequest anAddOfferRequest() {
            return AddOfferRequest.builder()
                    .title(DEFAULT_OFFER_NAME)
                    .company(DEFAULT_OFFER_COMPANY)
                    .salaryMin(100.0)
                    .salaryMax(200.0)
                    .url(DEFAULT_OFFER_URL)
                    .source(DEFAULT_SOURCE)
                    .salary_estimated(DEFAULT_SALARY_ESTIMATED)
                    .build();
        }

    }


    @Nested
    @DisplayName("addOffer - Tests")
    class AddOfferTests {
        @Test
        @DisplayName("Should return OfferResponseDto with message and offerDto.")
        public void should_return_OfferResponseDto_with_message_and_offerDto() {
            // Given
            AddOfferRequest addOfferRequest = TestEntityFactory.anAddOfferRequest();
            // When
            OfferResponseDto offerResponseDto = offerFacade.addOffer(mapFromAddOfferRequestToAddOfferRequestDto(addOfferRequest));
            assertThat(offerResponseDto).isNotNull();
            assertThat(offerResponseDto.message()).isEqualTo("Successfully saved Offer : Title: TestOffer | Company: TestCompany to database.");
            assertThat(offerResponseDto.offerDto()).isNotNull();
            assertThat(offerResponseDto.offerDto())
                    .extracting(OfferDto::title, OfferDto::company, OfferDto::salary, OfferDto::url, OfferDto::source)
                    .containsExactly(addOfferRequest.title(), addOfferRequest.company(),
                            String.format("%.0f - %.0f PLN", addOfferRequest.salaryMin(), addOfferRequest.salaryMax()),
                            addOfferRequest.url(), addOfferRequest.source());
            assertThat(offerFacade.findAllOffers().size()).isEqualTo(1);
        }

    }

    @Nested
    @DisplayName("findAllOffers - Tests")
    class FindAllOffersTest {
        @Test
        @DisplayName("Should return set of offers.")
        public void should_return_list_of_offers() {
            // Given
            for (int i = 0; i < 10; i++) {
                AddOfferRequest addOfferRequest = AddOfferRequest.builder()
                        .title("TestOffer" + i)
                        .company("TestCompany" + i)
                        .salaryMin(100.0)
                        .salaryMax(200.0)
                        .url("http://localhost:8080/" + i)
                        .source("TestOfferSource" + i)
                        .salary_estimated(true)
                        .build();
                offerFacade.addOffer(mapFromAddOfferRequestToAddOfferRequestDto(addOfferRequest));
            }
            assertThat(offerFacade.findAllOffers().size()).isEqualTo(10);
            // When
            List<OfferDto> allOffers = offerFacade.findAllOffers();
            // Then
            assertThat(allOffers).isNotNull();
            assertThat(allOffers.size()).isEqualTo(10);
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
            FetchOfferResponseDto fetchOfferResponseDto = offerFacade.fetchAllOffersAndSaveIfNotExists();
            // Then
            assertThat(fetchOfferResponseDto).isNotNull();
            assertThat(fetchOfferResponseDto.message()).isEqualTo("Fetched 3 new offers from external API.");
            assertThat(fetchOfferResponseDto.jobOffersList()).isNotNull();
            fetchOfferResponseDto.jobOffersList().forEach(jobOffer -> {
                System.out.println(
                                "Offer Id: " + jobOffer.id() + "\n" +
                                "Title: " + jobOffer.title() + "\n" +
                                "Company: " + jobOffer.company() + "\n" +
                                "Salary:  " + jobOffer.salary() + "\n" +
                                "OfferUrl: " + jobOffer.url() + "\n" +
                                "Source: " + jobOffer.source() + "\n  ----------");
            }); // Just for visual purposes. :)
        }

    }

    @Nested
    @DisplayName("findOfferById - Tests")
    class FindOfferByIdTest {
        @Test
        @DisplayName("Should return correct offer.")
        public void should_return_OfferResponseDto_with_message_and_null_offerDto() {
            // Given
            Offer offer = TestEntityFactory.anOffer();
            OfferResponseDto offerResponseDto = offerFacade.addOffer(mapFromAddOfferRequestToAddOfferRequestDto(TestEntityFactory.anAddOfferRequest()));
            String offerID = offerResponseDto.offerDto().id();
            // When
            OfferResponseDto offerById = offerFacade.findOfferById(offerID);
            // Then

            assertThat(offerById).isNotNull();
            assertThat(offerById.message()).isEqualTo("Offer with id " + offerID + " successfully found.");
            assertThat(offerById.offerDto()).isEqualTo(offerResponseDto.offerDto());

        }
        @Test
        @DisplayName("Should return OfferNotFoundException, when non existing id was given")
        public void should_throw_OfferNotFoundException_when_non_existing_offer_id() {
            // Given
            String nonExistingOfferId = "123456789012345678901234";
            // When
            Throwable throwable = catchThrowable(() -> offerFacade.findOfferById(nonExistingOfferId));
            // Then
            assertThat(throwable).isExactlyInstanceOf(OfferNotFoundException.class);
            assertThat(throwable).hasMessage("Offer with id " + nonExistingOfferId + " not found.");
        }

        @Test
        @DisplayName("Should return InvalidOfferIdException, when incorrect id was given")
        public void should_throw_InvalidOfferIdException_when_invalid_offer_id() {
            // Given
            // When
            Throwable throwable = catchThrowable(() -> offerFacade.findOfferById("1234"));
            // Then
            assertThat(throwable).isExactlyInstanceOf(InvalidOfferIdException.class);
            assertThat(throwable).hasMessage("Invalid offer id. Expected format: 24 characters, digits and letters a–f, e.g. 6a6a386a6a7fad2d161c487e");
        }

    }

    @Nested
    @DisplayName("addOffer - Tests")
    class AddOfferTest {
        @Test
        @DisplayName("Should return add offer and return OfferResponseClass with message and OfferDto")
        public void should_return_OfferResponseClass_with_message_and_offerDto() {

        }
    }
}
