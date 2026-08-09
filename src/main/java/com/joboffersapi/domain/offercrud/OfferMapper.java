package com.joboffersapi.domain.offercrud;

import com.joboffersapi.domain.offercrud.dto.AddOfferRequestDto;
import com.joboffersapi.domain.offercrud.dto.FetchedOffer;
import com.joboffersapi.domain.offercrud.dto.OfferDto;


final class OfferMapper {

    static OfferDto mapFromOfferToOfferDto(Offer offer) {
        return OfferDto.builder()
                .id(offer.getId())
                .title(offer.getTitle())
                .company(offer.getCompany())
                .salary(offer.getSalary())
                .url(offer.getUrl())
                .source(offer.getSource())
                .build();
    }

    static Offer mapFromAddOfferRequestDtoToOffer(AddOfferRequestDto addOfferRequestDto) {
        return Offer.builder()
                .title(addOfferRequestDto.title())
                .company(addOfferRequestDto.company())
                .salary(addOfferRequestDto.salary())
                .url(addOfferRequestDto.url())
                .source(addOfferRequestDto.source())
                .salary_estimated(addOfferRequestDto.salary_estimated())
                .build();
    }
    static Offer mapFromFetchedOfferToOffer(final FetchedOffer fetchedOffer) {
        return Offer.builder()
                .title(fetchedOffer.title())
                .company(fetchedOffer.company())
                .salary(fetchedOffer.salary())
                .url(fetchedOffer.offerUrl())
                .source(fetchedOffer.source())
                .salary_estimated(fetchedOffer.salaryEstimated())
                .build();
    }
}
