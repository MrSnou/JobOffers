package com.joboffersapi.domain.offerCRUD;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

class InMemoryOfferRepository implements OfferRepository {

    Map<Long, Offer> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);


    @Override
    public Offer save(final Offer offer) {
        long index = this.index.getAndIncrement();
        db.put(index, offer);
        offer.setId(Long.valueOf(index));
        return offer;
    }

    @Override
    public Optional<Offer> findById(final Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public void deleteById(final Long id) {
        db.remove(id);
    }

    @Override
    public Iterable<Offer> findAll() {
        return db.values();
    }

    @Override
    public boolean existsByUrl(final String url) {
        return db.values().stream()
                .anyMatch(offer -> offer.getUrl().equals(url));
    }
}
