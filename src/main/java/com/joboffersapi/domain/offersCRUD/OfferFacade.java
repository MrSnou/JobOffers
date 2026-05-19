package com.joboffersapi.domain.offersCRUD;

import com.joboffersapi.domain.offersCRUD.dto.AddOfferRequestDto;
import com.joboffersapi.domain.offersCRUD.dto.OfferDto;
import com.joboffersapi.domain.offersCRUD.dto.OfferResponseDto;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class OfferFacade {

    private final OfferReceiver offerReceiver;
    private final OfferAdder offerAdder;
    private final OfferFetcher offerFetcher;

    public OfferResponseDto addOffer(AddOfferRequestDto addOfferRequestDto) {
        return offerAdder.addOfferFromRequestDto(addOfferRequestDto);
    }

    public OfferResponseDto findOfferById(Long id) {
        return offerReceiver.findOfferById(id);
    }

    public Set<OfferDto> findAllOffers() {
        return offerReceiver.findAllOffers();
    }

    public OfferResponseDto fetchAllOffersAndSaveIfNotExists() {
        offerFetcher.findAllOffersFromSite();
        return new OfferResponseDto("Successfully fetched and saved offers from external API.", null);

    }
}
