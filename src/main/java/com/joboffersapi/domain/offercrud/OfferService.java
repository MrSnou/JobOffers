package com.joboffersapi.domain.offercrud;

import com.joboffersapi.domain.offercrud.dto.AddOfferRequestDto;
import com.joboffersapi.domain.offercrud.dto.FetchedOffer;
import com.joboffersapi.domain.offercrud.dto.OfferDto;
import com.joboffersapi.domain.offercrud.dto.OfferResponseDto;
import com.joboffersapi.domain.offercrud.exception.OfferNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.joboffersapi.domain.offercrud.OfferMapper.*;


@Service
@AllArgsConstructor
@Log4j2
class OfferService {

    private final OfferRepository offerRepository;
    private final OfferFetchable offerFetcher;

    public void addOffer(Offer offer) {
        offerRepository.save(offer);
    }

    /**
     * @return all offers including newly fetched offers from external API's.
     */
    List<OfferDto> findAllOffers() {
        log.info("OfferService | findAllOffers - fetching all offers from database and external API's.");
        List<OfferDto> allOffers = new ArrayList<>();
        fetchAndSaveNewOffers();
        findAllOffersFromDb().forEach(allOffers::add);
        return allOffers;
    }

    /**
     * @return offers from database.
     */
    List<OfferDto> findAllOffersFromDb() {
        log.info("OfferService | findAllOffersFromDb - fetching all offers from database.");
        return offerRepository.findAll().stream()
                .map(OfferMapper::mapFromOfferToOfferDto)
                .collect(Collectors.toList());
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

    /**
     * @return new offers added to database.
     */
    List<Offer> fetchAndSaveNewOffers() {
        log.info("OfferService | fetchAndSaveNewOffers - fetching all offers from external API.");
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
