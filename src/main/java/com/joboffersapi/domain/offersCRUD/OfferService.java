package com.joboffersapi.domain.offersCRUD;

import com.joboffersapi.domain.offersCRUD.dto.AddOfferRequestDto;
import com.joboffersapi.domain.offersCRUD.dto.OfferDto;
import com.joboffersapi.domain.offersCRUD.dto.OfferResponseDto;
import com.joboffersapi.domain.offersCRUD.exception.OfferNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.joboffersapi.domain.offersCRUD.OfferMapper.mapFromAddOfferRequestDtoToOffer;
import static com.joboffersapi.domain.offersCRUD.OfferMapper.mapFromOfferToOfferDto;


@Service
@AllArgsConstructor
class OfferService {

    private final OfferRepository offerRepository;

    public void addOffer(Offer offer) {
        offerRepository.save(offer);
    }

    public void addOffers(Iterable<Offer> offers) {
        offers.forEach(this::addOffer);
    }


    OfferResponseDto addOfferFromRequestDto(final AddOfferRequestDto addOfferRequestDto) {
        Offer offerToSave = mapFromAddOfferRequestDtoToOffer(addOfferRequestDto);
        Offer savedOffer = offerRepository.save(offerToSave);
        return new OfferResponseDto("Successfully saved Offer : \n" +
                "Title: " + savedOffer.getTitle() + "\n" +
                "Description: " + savedOffer.getDescription() + "\n" +
                "to database.", mapFromOfferToOfferDto(savedOffer));
    }

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
