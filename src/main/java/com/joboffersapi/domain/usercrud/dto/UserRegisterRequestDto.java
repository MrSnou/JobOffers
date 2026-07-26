package com.joboffersapi.domain.usercrud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserRegisterRequestDto(
        @NotEmpty
        @NotNull
        @Size(min = 3, max = 30)
        String username,

        @Email
        @NotEmpty
        String email,

        @NotEmpty
        @NotNull
        @Size(min = 6, max = 50)
        String password) {
}
