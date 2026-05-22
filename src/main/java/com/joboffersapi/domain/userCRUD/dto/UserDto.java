package com.joboffersapi.domain.userCRUD.dto;

import lombok.Builder;

@Builder
public record UserDto(Long id, String username, String email) {
}
