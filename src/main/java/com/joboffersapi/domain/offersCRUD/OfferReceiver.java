package com.joboffersapi.domain.offersCRUD;

import com.joboffersapi.domain.offersCRUD.dto.OfferDto;
import com.joboffersapi.domain.offersCRUD.dto.OfferResponseDto;
import com.joboffersapi.domain.offersCRUD.exception.OfferNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
class OfferReceiver {

    private final OfferRepository offerRepository;

    OfferResponseDto findOfferById(final Long id) {
        Offer offerById = offerRepository.findById(id).
                orElseThrow(() -> new OfferNotFoundException("Offer with id " + id + " not found."));
        return OfferResponseDto.builder()
                .message("Offer with id " + id + " successfully found.")
                .offerDto(OfferMapper.mapFromOfferToOfferDto(offerById))
                .build();
    }

    Set<OfferDto> findAllOffers() {
        Set<OfferDto> offers = new HashSet<>();
        offerRepository.findAll().forEach(
                offer -> offers.add(OfferMapper.mapFromOfferToOfferDto(offer))
        );
        return offers;
    }


}
