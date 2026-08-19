package com.joboffersapi.infrastructure.usercrud.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record JwtResponseDto(
        @NotBlank(message = "Username cannot be empty.")
        String username,
        @NotBlank(message = "Password cannot be empty.")
        String password
) {
}
