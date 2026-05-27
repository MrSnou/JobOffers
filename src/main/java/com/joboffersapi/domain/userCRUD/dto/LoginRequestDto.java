package com.joboffersapi.domain.userCRUD.dto;

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
