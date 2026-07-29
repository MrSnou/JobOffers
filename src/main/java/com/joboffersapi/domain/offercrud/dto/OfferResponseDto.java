package com.joboffersapi.domain.offercrud.dto;

import lombok.Builder;

@Builder
public record OfferResponseDto(String message, OfferDto offerDto) {
}
