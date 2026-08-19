package com.joboffersapi.domain.usercrud.dto;

import lombok.Builder;

@Builder
public record LoginRequest(
        String username,
        String password) {
}
