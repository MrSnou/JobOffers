package com.joboffersapi.domain.offersCRUD;

import org.springframework.data.repository.Repository;

import java.net.URL;
import java.util.Optional;

interface OfferRepository extends Repository<Offer, Long> {

     Offer save(Offer offer);

     Optional<Offer> findById(Long id);

     void deleteById(Long id);

     Iterable<Offer> findAll();

     boolean existsByUrl(URL url);
}
