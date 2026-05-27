package com.joboffersapi.domain.offerCRUD;

import com.joboffersapi.domain.offerCRUD.dto.JobOfferResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class OfferFetcher implements OfferFetchable{

    private final OfferRepository offerRepository;

    @Override
    public List<JobOfferResponse> fetchOffers() {
        return List.of();
    }
}
