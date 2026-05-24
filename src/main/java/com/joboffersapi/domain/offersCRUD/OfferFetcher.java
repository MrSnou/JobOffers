package com.joboffersapi.domain.offersCRUD;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static com.joboffersapi.domain.offersCRUD.OfferMapper.mapFromJsonToOffers;

@Service
@RequiredArgsConstructor
class OfferFetcher {

    private final RestTemplate restTemplate;
    private final OfferRepository offerRepository;

    public void findAllOffersFromSite() {
        String jsonResponse = restTemplate.execute(
                "https://www.google.pl",
                HttpMethod.GET,
                null,
                null,
                String.class
        );

        Set<Offer> offers = mapFromJsonToOffers(jsonResponse);
        offers.forEach(this::saveOfferIfNotExist);
    }

    private void saveOfferIfNotExist(Offer offer) {
        if (!offerRepository.existsByUrl(offer.getUrl())) {
            offerRepository.save(offer);
        }
    }
}
