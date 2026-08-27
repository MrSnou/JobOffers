package com.joboffersapi.infrastructure.usercrud.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserRegisterRequestDto(
        @NotEmpty(message = "Username cannot be empty.")
        @NotNull(message = "Username cannot be empty.")
        @Size(min = 3, max = 30, message = "Username size have to be between 3 and 30 characters.")
        String username,

        @NotEmpty(message = "Password cannot be empty.")
        @NotNull(message = "Password cannot be empty.")
        @Size(min = 6, max = 50, message = "Password size have to be between 6 and 50 characters.")
        String password) {
}
