package com.joboffersapi.domain.offersCRUD.dto;

import lombok.Builder;

import java.net.URL;

@Builder
public record OfferDto(
        Long id,
        String title,
        String description,
        double salary,
        URL url
) {
}
