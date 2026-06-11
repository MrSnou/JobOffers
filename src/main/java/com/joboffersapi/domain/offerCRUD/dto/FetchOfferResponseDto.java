package com.joboffersapi.domain.offerCRUD.dto;

import lombok.Builder;

import java.util.List;


@Builder
public record FetchOfferResponseDto(String message, List<OfferDto> jobOffersList) {
}
