package com.joboffersapi.domain.offersCRUD;

import com.joboffersapi.domain.offersCRUD.dto.AddOfferRequestDto;
import com.joboffersapi.domain.offersCRUD.dto.OfferDto;

import java.util.Set;

class OfferMapper {

    static Set<Offer> mapFromJsonToOffers(String jsonResponse) {
        // some sexy logic :)
        return Set.of();
    }

    static OfferDto mapFromOfferToOfferDto(Offer offer) {
        return OfferDto.builder()
                .id(offer.getId())
                .title(offer.getTitle())
                .description(offer.getDescription())
                .salary(offer.getSalary())
                .url(offer.getUrl())
                .build();
    }

    static Offer mapFromDtoToOffer(OfferDto offerDto) {
        return Offer.builder()
                .title(offerDto.title())
                .description(offerDto.description())
                .salary(offerDto.salary())
                .url(offerDto.url())
                .build();
    }

    static Offer mapFromAddOfferRequestDtoToOffer(AddOfferRequestDto addOfferRequestDto) {
        return Offer.builder()
                .title(addOfferRequestDto.title())
                .description(addOfferRequestDto.description())
                .salary(addOfferRequestDto.salary())
                .url(addOfferRequestDto.url())
                .build();
    }
}
