package com.joboffersapi.domain.offerCRUD;

import com.joboffersapi.domain.offerCRUD.dto.AddOfferRequestDto;
import com.joboffersapi.domain.offerCRUD.dto.FetchOfferResponseDto;
import com.joboffersapi.domain.offerCRUD.dto.OfferDto;
import com.joboffersapi.domain.offerCRUD.dto.OfferResponseDto;
import com.joboffersapi.domain.offerCRUD.exception.OfferNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class OfferFacadeTest {

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

        static AddOfferRequestDto anAddOfferRequestDto(Offer offer) {
            return AddOfferRequestDto.builder()
                    .title(offer.getTitle())
                    .company(offer.getCompany())
                    .salary(offer.getSalary())
                    .url(offer.getUrl())
                    .source(offer.getSource())
                    .salary_estimated(offer.isSalary_estimated())
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
            Offer offer = TestEntityFactory.anOffer();
            AddOfferRequestDto addOfferRequestDto = TestEntityFactory.anAddOfferRequestDto(offer);
            // When
            OfferResponseDto offerResponseDto = offerFacade.addOffer(addOfferRequestDto);
            assertThat(offerResponseDto).isNotNull();
            assertThat(offerResponseDto.message()).isEqualTo("Successfully saved Offer : \n" +
                    "Title: " + offer.getTitle() + "\n" +
                    "Company: " + offer.getCompany() + "\n" +
                    "to database.");
            assertThat(offerResponseDto.offerDto()).isNotNull();
            assertThat(offerResponseDto.offerDto())
                    .extracting(OfferDto::title, OfferDto::company, OfferDto::salary, OfferDto::url)
                    .containsExactly(offer.getTitle(), offer.getCompany(), offer.getSalary(), offer.getUrl());
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
                    .extracting(OfferDto::title, OfferDto::salary, OfferDto::url)
                    .containsExactly(offer.getTitle(), offer.getSalary(), offer.getUrl());

        }

        @Test
        @DisplayName("Should throw OfferNotFoundException when offer with given id does not exist.")
        public void should_throw_OfferNotFoundException_when_offer_with_given_id_does_not_exist() {
            // Given
            String nonExistingId = "999L";
            // When
            Throwable throwable = catchThrowable(() -> offerFacade.findOfferById(nonExistingId));
            // Then
            assertThat(throwable).hasMessage("Offer with id " + nonExistingId + " not found.")
                    .isExactlyInstanceOf(OfferNotFoundException.class);

        }
    }

    @Nested
    @DisplayName("findAllOffers - Tests")
    class FindAllOffersTest {
        @Test
        @DisplayName("Should return set of offers.")
        public void should_return_set_of_offers() {
            // Given
            for (int i = 0; i < 10; i++) {
                Offer offer = Offer.builder()
                        .title("TestOffer" + i)
                        .company("TestCompany" + i)
                        .salary("67.67" + i)
                        .url("http://localhost:8080/" + i)
                        .build();
                offerFacade.addOffer(TestEntityFactory.anAddOfferRequestDto(offer));
            }
            assertThat(offerFacade.findAllOffers().size()).isEqualTo(10);
            // When
            Set<OfferDto> allOffers = offerFacade.findAllOffers();
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
}
