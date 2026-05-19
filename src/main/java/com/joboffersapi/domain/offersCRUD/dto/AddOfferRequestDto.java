package com.joboffersapi.domain.offersCRUD.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.net.URL;

public record AddOfferRequestDto(
        @NotNull
        @NotEmpty
        @Size(min = 1, max = 30)
        String title,

        @NotNull
        @NotEmpty
        @Size(min = 1, max = 300)
        String description,

        @NotNull
        @NotEmpty
        @PositiveOrZero
        double salary,
        @NotNull
        @PositiveOrZero
        URL url
) {
}
