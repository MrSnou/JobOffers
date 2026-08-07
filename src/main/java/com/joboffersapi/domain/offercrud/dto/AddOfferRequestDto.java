package com.joboffersapi.domain.offercrud.dto;

import lombok.Builder;

@Builder
public record AddOfferRequestDto(
        String title,

        String company,

        String salary,

        String url,

        String source,

        boolean salary_estimated
) {}
