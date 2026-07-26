package com.joboffersapi.domain.usercrud.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDto(
        @NotNull
        @NotEmpty
        String email,
        @NotNull
        @NotEmpty
        String password) {
}
