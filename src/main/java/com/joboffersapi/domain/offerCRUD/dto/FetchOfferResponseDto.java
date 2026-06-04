package com.joboffersapi.domain.offerCRUD.dto;

import lombok.Builder;

@Builder
public record FetchOfferResponseDto(String message, Iterable<OfferDto> jobOffersList) {
}
