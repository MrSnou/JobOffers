package com.joboffersapi.domain.offersCRUD.dto;

import lombok.Builder;

@Builder
public record OfferResponseDto(String message, OfferDto offerDto) {
}
