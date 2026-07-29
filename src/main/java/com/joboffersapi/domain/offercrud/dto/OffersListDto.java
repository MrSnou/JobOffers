package com.joboffersapi.domain.offercrud.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record OffersListDto(List<OfferDto> offers) {
}
