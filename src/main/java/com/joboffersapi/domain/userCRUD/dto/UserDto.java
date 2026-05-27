package com.joboffersapi.domain.userCRUD.dto;

import lombok.Builder;

@Builder
public record UserDto(String id, String username, String email) {
}
