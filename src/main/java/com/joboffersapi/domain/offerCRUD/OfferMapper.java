package com.joboffersapi.domain.offerCRUD;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joboffersapi.domain.offerCRUD.dto.AddOfferRequestDto;
import com.joboffersapi.domain.offerCRUD.dto.JobOfferResponse;
import com.joboffersapi.domain.offerCRUD.dto.OfferDto;
import com.joboffersapi.domain.offerCRUD.exception.RemoteServerDataMappingException;

import java.util.List;

class OfferMapper {

    static List<JobOfferResponse> mapFromJsonToOffers(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(
                    json,
                    new TypeReference<List<JobOfferResponse>>() {
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
                .build();
    }

    static Offer mapFromAddOfferRequestDtoToOffer(AddOfferRequestDto addOfferRequestDto) {
        return Offer.builder()
                .title(addOfferRequestDto.title())
                .company(addOfferRequestDto.company())
                .salary(addOfferRequestDto.salary())
                .url(addOfferRequestDto.url())
                .build();
    }
}
