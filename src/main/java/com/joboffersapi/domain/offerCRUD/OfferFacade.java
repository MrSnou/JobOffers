package com.joboffersapi.domain.offerCRUD;

import com.joboffersapi.domain.offerCRUD.dto.AddOfferRequestDto;
import com.joboffersapi.domain.offerCRUD.dto.FetchOfferResponseDto;
import com.joboffersapi.domain.offerCRUD.dto.OfferDto;
import com.joboffersapi.domain.offerCRUD.dto.OfferResponseDto;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
                        .collect(Collectors.toSet()))
                .build();
    }
}
