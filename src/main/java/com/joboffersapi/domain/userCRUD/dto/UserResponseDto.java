package com.joboffersapi.domain.userCRUD.dto;

import lombok.Builder;

@Builder
public record UserResponseDto(String message, UserDto userDto) {
}
