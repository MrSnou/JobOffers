package com.joboffersapi.domain.offersCRUD;

import com.joboffersapi.domain.offersCRUD.dto.AddOfferRequestDto;
import com.joboffersapi.domain.offersCRUD.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.joboffersapi.domain.offersCRUD.OfferMapper.mapFromAddOfferRequestDtoToOffer;
import static com.joboffersapi.domain.offersCRUD.OfferMapper.mapFromOfferToOfferDto;


@Service
@AllArgsConstructor
@Transactional
class OfferAdder {

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
}
