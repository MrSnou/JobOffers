package com.joboffersapi.infrastructure.offercrud.util;

import com.joboffersapi.domain.offercrud.dto.AddOfferRequestDto;
import com.joboffersapi.infrastructure.offercrud.dto.AddOfferRequest;

public final class HttpLayerOfferMapper {

    private HttpLayerOfferMapper() {}

    /**
     *
     * @return correct domain AddOfferObject.
     */

    public static AddOfferRequestDto mapFromAddOfferRequestToAddOfferRequestDto(AddOfferRequest addOfferRequest) {
        return AddOfferRequestDto.builder()
                .title(addOfferRequest.title())
                .company(addOfferRequest.company())
                .salary(String.format("%.0f - %.0f PLN", addOfferRequest.salaryMin(), addOfferRequest.salaryMax()))
                .url(addOfferRequest.url())
                .source(addOfferRequest.source())
                .salary_estimated(addOfferRequest.salary_estimated())
                .build();

    }
}
