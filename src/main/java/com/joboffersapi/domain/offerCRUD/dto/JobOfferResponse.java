package com.joboffersapi.domain.offerCRUD.dto;

import lombok.Builder;

@Builder
public record JobOfferResponse(String title, String company, Double salary, String offerUrl) {
}
