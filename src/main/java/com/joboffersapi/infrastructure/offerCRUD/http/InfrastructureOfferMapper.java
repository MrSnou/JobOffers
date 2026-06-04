package com.joboffersapi.infrastructure.offerCRUD.http;

import com.joboffersapi.domain.offerCRUD.dto.FetchedOffer;
import com.joboffersapi.infrastructure.offerCRUD.http.dto.JobOfferFromApi;

class InfrastructureOfferMapper {

    public static FetchedOffer mapFromJobOfferFromApiToFetchedOffer(JobOfferFromApi jobOfferFromApi) {
        return new FetchedOffer(
                jobOfferFromApi.title(),
                jobOfferFromApi.company(),
                jobOfferFromApi.salary(),
                jobOfferFromApi.offerUrl(),
                jobOfferFromApi.source(),
                jobOfferFromApi.salaryEstimated()
        );
    }

}
