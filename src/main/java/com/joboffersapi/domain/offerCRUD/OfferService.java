package com.joboffersapi.domain.offerCRUD;

import com.joboffersapi.domain.offerCRUD.dto.AddOfferRequestDto;
import com.joboffersapi.domain.offerCRUD.dto.FetchedOffer;
import com.joboffersapi.domain.offerCRUD.dto.OfferDto;
import com.joboffersapi.domain.offerCRUD.dto.OfferResponseDto;
import com.joboffersapi.domain.offerCRUD.exception.OfferNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.joboffersapi.domain.offerCRUD.OfferMapper.*;


@Service
@AllArgsConstructor
class OfferService {

    private final OfferRepository offerRepository;
    private final OfferFetchable offerFetcher;

    public void addOffer(Offer offer) {
        offerRepository.save(offer);
    }

    Set<OfferDto> findAllOffers() {
        return offerRepository.findAll().stream()
                .map(OfferMapper::mapFromOfferToOfferDto)
                .collect(Collectors.toSet());
    }

    OfferResponseDto addOfferFromOfferRequestDto(final AddOfferRequestDto addOfferRequestDto) {
        Offer offerToSave = mapFromJobOfferDtoToOffer(addOfferRequestDto);
        Offer savedOffer = offerRepository.save(offerToSave);
        return new OfferResponseDto("Successfully saved Offer : \n" +
                "Title: " + savedOffer.getTitle() + "\n" +
                "Company: " + savedOffer.getCompany() + "\n" +
                "to database.", mapFromOfferToOfferDto(savedOffer));
    }

    OfferResponseDto findOfferById(final String id) {
        Offer offerById = offerRepository.findById(id).
                orElseThrow(() -> new OfferNotFoundException("Offer with id " + id + " not found."));
        return OfferResponseDto.builder()
                .message("Offer with id " + id + " successfully found.")
                .offerDto(mapFromOfferToOfferDto(offerById))
                .build();
    }

    List<Offer> fetchAndSaveNewOffers() {
        List<FetchedOffer> fetchedOffers = offerFetcher.fetchOffers();
        List<Offer> newlyAdded = new ArrayList<>();
        for (FetchedOffer fetchedOffer : fetchedOffers) {
            Offer offer = mapFromFetchedOfferToOffer(fetchedOffer);
            if (saveOfferIfNotExist(offer)) {
                newlyAdded.add(offer);
            }
        }
        return newlyAdded;
    }



    private Boolean saveOfferIfNotExist(Offer offer) {
        if (!offerRepository.existsByUrl(offer.getUrl())) {
            offerRepository.save(offer);
            return true;
        }
        return false;
    }


}
