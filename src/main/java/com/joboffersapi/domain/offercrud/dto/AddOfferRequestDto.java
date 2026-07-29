package com.joboffersapi.domain.offercrud.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AddOfferRequestDto(
        @NotNull
        @NotEmpty
        @Size(min = 1, max = 30)
        String title,

        @NotNull
        @NotEmpty
        @Size(min = 1, max = 50)
        String company,

        @NotNull
        @NotEmpty
        @PositiveOrZero
        String salary,

        @NotNull
        String url,

        @NotNull
        String source,

        Boolean salary_estimated
) {
}
