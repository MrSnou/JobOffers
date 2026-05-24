package com.joboffersapi.domain.userCRUD;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


class InMemoryUserRepository implements UserRepository {

    Map<Long, User> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);

    @Override
    public User save(final User offer) {
        long index = this.index.getAndIncrement();
        db.put(index, offer);
        offer.setId(Long.valueOf(index));
        return offer;
    }

    @Override
    public User findByUsername(final String username) {
        return db.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public boolean existsByUsername(final String username) {
        return db.values().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }
}
