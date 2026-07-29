package com.joboffersapi.infrastructure.offercrud.http.scheduler;

import com.joboffersapi.domain.offercrud.OfferFacade;
import com.joboffersapi.domain.offercrud.dto.FetchOfferResponseDto;
import com.joboffersapi.domain.offercrud.dto.OfferDto;
import com.joboffersapi.domain.offercrud.dto.OfferListResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Log4j2
class JobOffersScheduler {

    private final OfferFacade offerFacade;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    @Scheduled(cron = "${job_offers.scheduler.time-cron}")
    public OfferListResponseDto fetchJobOffers() {
        log.info("Scheduler started: Fetching job offers from external APIs {}", dateFormat.format(new Date()));
        FetchOfferResponseDto fetchOfferResponseDto = offerFacade.fetchAllOffersAndSaveIfNotExists();
        List<OfferDto> offerDtoSet = fetchOfferResponseDto.jobOffersList();
        int size = offerDtoSet.size();
        log.info("Scheduler finished: {} Job offers fetched and saved to the database", size);
        return OfferListResponseDto.builder()
                .offers(offerDtoSet)
                .build();
    }
}
