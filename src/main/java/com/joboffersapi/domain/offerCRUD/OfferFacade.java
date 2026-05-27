package com.joboffersapi.domain.offerCRUD;

import com.joboffersapi.domain.offerCRUD.dto.AddOfferRequestDto;
import com.joboffersapi.domain.offerCRUD.dto.FetchOfferResponseDto;
import com.joboffersapi.domain.offerCRUD.dto.JobOfferResponse;
import com.joboffersapi.domain.offerCRUD.dto.OfferDto;
import com.joboffersapi.domain.offerCRUD.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@AllArgsConstructor
public class OfferFacade {

    private final OfferService offerService;

    public OfferResponseDto addOffer(AddOfferRequestDto addOfferRequestDto) {
        return offerService.addOfferFromRequestDto(addOfferRequestDto);
    }

    public OfferResponseDto findOfferById(String id) {
        return offerService.findOfferById(id);
    }

    public Set<OfferDto> findAllOffers() {
        return offerService.findAllOffers();
    }

    @Transactional
    public FetchOfferResponseDto fetchAllOffersAndSaveIfNotExists() {
        Iterable<JobOfferResponse> jobOfferResponses = offerService.fetchOffers();
        return FetchOfferResponseDto.builder()
                .message("Successfully fetched and saved offers from external API.")
                .jobOffersList(jobOfferResponses)
                .build();
    }
}
