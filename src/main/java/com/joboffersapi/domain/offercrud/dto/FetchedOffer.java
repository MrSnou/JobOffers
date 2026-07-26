package com.joboffersapi.domain.offercrud.dto;

import lombok.Builder;

@Builder
public record FetchedOffer(
        String title,
        String company,
        String salary,
        String offerUrl,
        String source,
        boolean salaryEstimated
) {
}
