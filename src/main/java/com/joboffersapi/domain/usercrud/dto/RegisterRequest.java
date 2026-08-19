package com.joboffersapi.domain.usercrud.dto;

import lombok.Builder;

@Builder
public record RegisterRequest(
        String username,
        String password) {
}
