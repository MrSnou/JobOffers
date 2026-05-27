package com.joboffersapi.domain.offerCRUD.dto;

import lombok.Builder;

@Builder
public record OfferDto(
        String id,
        String title,
        String company,
        double salary,
        String url
) {
}
