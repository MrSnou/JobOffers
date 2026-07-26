package com.joboffersapi.domain.usercrud.dto;

import lombok.Builder;

@Builder
public record UserDto(String id, String username, String email) {
}
