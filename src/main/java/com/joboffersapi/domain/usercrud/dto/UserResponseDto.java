package com.joboffersapi.domain.usercrud.dto;

import lombok.Builder;

@Builder
public record UserResponseDto(String message, UserDto userDto) {
}
