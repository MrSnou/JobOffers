package com.joboffersapi.domain.offerCRUD;

import com.joboffersapi.domain.offerCRUD.dto.AddOfferRequestDto;
import com.joboffersapi.domain.offerCRUD.dto.JobOfferResponse;
import com.joboffersapi.domain.offerCRUD.dto.OfferDto;
import com.joboffersapi.domain.offerCRUD.dto.OfferResponseDto;
import com.joboffersapi.domain.offerCRUD.exception.OfferNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.joboffersapi.domain.offerCRUD.OfferMapper.mapFromAddOfferRequestDtoToOffer;
import static com.joboffersapi.domain.offerCRUD.OfferMapper.mapFromOfferToOfferDto;


@Service
@AllArgsConstructor
class OfferService {

    private final OfferRepository offerRepository;
    private final OfferFetchable offerFetcher;

    public void addOffer(Offer offer) {
        offerRepository.save(offer);
    }


    OfferResponseDto addOfferFromRequestDto(final AddOfferRequestDto addOfferRequestDto) {
        Offer offerToSave = mapFromAddOfferRequestDtoToOffer(addOfferRequestDto);
        Offer savedOffer = offerRepository.save(offerToSave);
        return new OfferResponseDto("Successfully saved Offer : \n" +
                "Title: " + savedOffer.getTitle() + "\n" +
                "Company: " + savedOffer.getCompany() + "\n" +
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



    public List<JobOfferResponse> fetchOffers() {
        List<JobOfferResponse> fetchedOffers = offerFetcher.fetchOffers();
        List<JobOfferResponse> savedOffers = new ArrayList<>();
        for (JobOfferResponse jobOfferResponse : fetchedOffers) {
            Offer offer = Offer.builder()
                    .title(jobOfferResponse.title())
                    .company(jobOfferResponse.company())
                    .salary(jobOfferResponse.salary())
                    .url(jobOfferResponse.offerUrl())
                    .build();
            if (saveOfferIfNotExist(offer)) {
                savedOffers.add(jobOfferResponse);
            }
        }
        return savedOffers;
    }

    private Boolean saveOfferIfNotExist(Offer offer) {
        if (!offerRepository.existsByUrl(offer.getUrl())) {
            offerRepository.save(offer);
            return true;
        }
        return false;
    }
}
