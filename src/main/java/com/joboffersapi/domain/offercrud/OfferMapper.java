package com.joboffersapi.domain.offercrud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joboffersapi.domain.offercrud.dto.AddOfferRequestDto;
import com.joboffersapi.domain.offercrud.dto.FetchedOffer;
import com.joboffersapi.domain.offercrud.dto.OfferDto;
import com.joboffersapi.domain.offercrud.exception.RemoteServerDataMappingException;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
class OfferMapper {

    static ObjectMapper objectMapper;

    static List<FetchedOffer> mapFromJsonToOffers(String json) {
        try {
            return objectMapper.readValue(
                    json,
                    new TypeReference<List<FetchedOffer>>() {
                    }
            );
        } catch (JsonProcessingException e) {
            throw new RemoteServerDataMappingException("Error while mapping JSON to Offers: " + e.getMessage());
        }
    }

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

    static Offer mapFromJobOfferDtoToOffer(AddOfferRequestDto addOfferRequestDto) {
        return Offer.builder()
                .title(addOfferRequestDto.title())
                .company(addOfferRequestDto.company())
                .salary(addOfferRequestDto.salary())
                .url(addOfferRequestDto.url())
                .source(addOfferRequestDto.source())
                .salary_estimated(addOfferRequestDto.salary_estimated())
                .build();
    }

    public static Offer mapFromFetchedOfferToOffer(final FetchedOffer fetchedOffer) {
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
