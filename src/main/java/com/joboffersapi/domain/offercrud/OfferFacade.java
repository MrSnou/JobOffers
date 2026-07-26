package com.joboffersapi.domain.offercrud;

import com.joboffersapi.domain.offercrud.dto.AddOfferRequestDto;
import com.joboffersapi.domain.offercrud.dto.FetchOfferResponseDto;
import com.joboffersapi.domain.offercrud.dto.OfferDto;
import com.joboffersapi.domain.offercrud.dto.OfferResponseDto;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class OfferFacade {

    private final OfferService offerService;

    public OfferResponseDto addOffer(AddOfferRequestDto addOfferRequestDto) {
        return offerService.addOfferFromOfferRequestDto(addOfferRequestDto);
    }

    public OfferResponseDto findOfferById(String id) {
        return offerService.findOfferById(id);
    }

    public Set<OfferDto> findAllOffers() {
        return offerService.findAllOffers();
    }

    public FetchOfferResponseDto fetchAllOffersAndSaveIfNotExists() {
        List<Offer> newlyFetchedOffers = offerService.fetchAndSaveNewOffers();
        return FetchOfferResponseDto.builder()
                .message("Fetched " + newlyFetchedOffers.size() + " new offers from external API.")
                .jobOffersList(newlyFetchedOffers.stream()
                        .map(OfferMapper::mapFromOfferToOfferDto)
                        .toList())
                .build();
    }
}
