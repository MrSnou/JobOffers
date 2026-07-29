package com.joboffersapi.infrastructure.offercrud.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record JobOfferFromApi (
        String title,
        String company,
        String salary,
        String offerUrl,
        String source,
        @JsonProperty("salary_estimated")
        boolean salaryEstimated) {
}
