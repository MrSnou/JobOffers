package com.joboffersapi.domain.offerCRUD.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.net.URL;

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
        double salary,

        @NotNull
        String url
) {
}
