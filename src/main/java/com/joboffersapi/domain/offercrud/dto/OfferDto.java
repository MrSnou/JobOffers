package com.joboffersapi.domain.offercrud.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record OfferDto(
        String id,
        String title,
        String company,
        String salary,
        String url,
        String source
) implements Serializable {
}
