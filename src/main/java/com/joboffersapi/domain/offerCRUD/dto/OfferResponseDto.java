package com.joboffersapi.domain.offerCRUD.dto;

import lombok.Builder;

@Builder
public record OfferResponseDto(String message, OfferDto offerDto) {
}
