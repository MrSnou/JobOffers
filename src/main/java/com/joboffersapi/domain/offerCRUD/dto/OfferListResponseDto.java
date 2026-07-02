package com.joboffersapi.domain.offerCRUD.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record OfferListResponseDto(List<OfferDto> offers) {
}
