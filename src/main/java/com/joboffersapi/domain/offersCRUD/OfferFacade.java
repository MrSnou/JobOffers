package com.joboffersapi.domain.offersCRUD;

import com.joboffersapi.domain.offersCRUD.dto.AddOfferRequestDto;
import com.joboffersapi.domain.offersCRUD.dto.OfferDto;
import com.joboffersapi.domain.offersCRUD.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@AllArgsConstructor
public class OfferFacade {

    private final OfferService offerService;
    private final OfferFetcher offerFetcher;

    public OfferResponseDto addOffer(AddOfferRequestDto addOfferRequestDto) {
        return offerService.addOfferFromRequestDto(addOfferRequestDto);
    }

    public OfferResponseDto findOfferById(Long id) {
        return offerService.findOfferById(id);
    }

    public Set<OfferDto> findAllOffers() {
        return offerService.findAllOffers();
    }

    @Transactional
    public OfferResponseDto fetchAllOffersAndSaveIfNotExists() {
        offerFetcher.findAllOffersFromSite();
        return new OfferResponseDto("Successfully fetched and saved offers from external API.", null);
    }
}
