package com.joboffersapi.infrastructure.usercrud.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "Username cannot be empty.")
        String username,
        @NotBlank(message = "Password cannot be empty.")
        String password) {
}
