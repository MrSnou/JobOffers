package com.joboffersapi.domain.offercrud;

import com.joboffersapi.domain.offercrud.dto.AddOfferRequestDto;
import com.joboffersapi.domain.offercrud.dto.FetchOfferResponseDto;
import com.joboffersapi.domain.offercrud.dto.OfferDto;
import com.joboffersapi.domain.offercrud.dto.OfferResponseDto;
import com.joboffersapi.domain.offercrud.dto.OffersListDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Log4j2
public class OfferFacade {

    private final OfferService offerService;

    public OfferResponseDto addOffer(AddOfferRequestDto addOfferRequestDto) {
        return offerService.addOfferFromOfferRequestDto(addOfferRequestDto);
    }

    public OfferResponseDto findOfferById(String id) {
        return offerService.findOfferById(id);
    }

    @Cacheable("jobOffers")
    public List<OfferDto> findAllOffers() {
        return offerService.findAllOffersFromDb();
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

    @Transactional
    public OffersListDto findAllOffersAndRefreshDatabase() {
        return OffersListDto.builder()
                .offers(offerService.findAllOffers())
                .build();

    }
}
