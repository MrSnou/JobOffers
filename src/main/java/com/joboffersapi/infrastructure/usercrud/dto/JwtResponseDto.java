package com.joboffersapi.infrastructure.usercrud.dto;

import lombok.Builder;

@Builder
public record JwtResponseDto(
        String token,
        String username
) {
}
