package com.joboffersapi.domain.offerCRUD.dto;

import lombok.Builder;

@Builder
public record OfferDto(
        Long id,
        String title,
        String company,
        double salary,
        String url
) {
}
