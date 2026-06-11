package com.joboffersapi.infrastructure.offerCRUD.http.scheduler;

import com.joboffersapi.domain.offerCRUD.OfferFacade;
import com.joboffersapi.domain.offerCRUD.dto.FetchOfferResponseDto;
import com.joboffersapi.domain.offerCRUD.dto.OfferDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
class JobOffersScheduler {

    private final OfferFacade offerFacade;


    @Scheduled(cron = "${job_offers.scheduler.time-cron}")
    public void fetchJobOffers() {
        log.info("Scheduler started: Fetching job offers from external APIs");
        FetchOfferResponseDto fetchOfferResponseDto = offerFacade.fetchAllOffersAndSaveIfNotExists();
        List<OfferDto> offerDtoSet = fetchOfferResponseDto.jobOffersList();
        int size = offerDtoSet.size();
        log.info("Scheduler finished: {} Job offers fetched and saved to the database", size);
    }
}
