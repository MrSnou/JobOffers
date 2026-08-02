package com.joboffersapi.infrastructure.offercrud.controller;

import com.joboffersapi.domain.offercrud.OfferFacade;
import com.joboffersapi.domain.offercrud.dto.OffersListDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/offers")
public class JobOfferRestController {

    OfferFacade offerFacade;

    @GetMapping
    public ResponseEntity<OffersListDto> offers() {
        log.info("/offers - Endpoint hit!");
        return ResponseEntity.ok(OffersListDto.builder()
                        .offers(offerFacade.findAllOffers())
                        .build());
    }

    @GetMapping("/refreshAndGetOffers")
    public ResponseEntity<OffersListDto> refreshAndGetOffers() {
        log.info("/refreshAndGetOffers - Endpoint hit!");
        return ResponseEntity.ok(offerFacade.findAllOffersAndRefreshDatabase());

    }
}
