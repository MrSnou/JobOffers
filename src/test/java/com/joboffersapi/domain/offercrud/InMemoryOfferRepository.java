package com.joboffersapi.domain.offercrud;

import com.joboffersapi.domain.offercrud.exception.InvalidOfferIdException;
import org.bson.types.ObjectId;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class InMemoryOfferRepository implements OfferRepository {

    Map<String, Offer> database = new ConcurrentHashMap<>();

    @Override
    public <S extends Offer> S save(S entity) {
        if (database.values().stream().anyMatch(offer -> offer.getUrl().equals(entity.getUrl()))) {
            throw new DuplicateKeyException(String.format("Offer with offerUrl [%s] already exists", entity.getUrl()));
        }
        String id = UUID.randomUUID().toString().replace("-", "").substring(0, 24); // Mongo HexString ID imitation.
        Offer offer = Offer.builder()
                .id(id)
                .company(entity.getCompany())
                .title(entity.getTitle())
                .salary(entity.getSalary())
                .url(entity.getUrl())
                .source(entity.getSource())
                .salary_estimated(entity.isSalary_estimated())
                .build();
        database.put(id, offer);
        return (S) offer;
    }

    @Override
    public Optional<Offer> findById(final String id) {
        if (!ObjectId.isValid(id)) { // Mongo constraint HexString ID check.
            throw new InvalidOfferIdException();
        }
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public boolean existsById(final String aLong) {
        return database.containsKey(aLong);
    }

    @Override
    public void deleteById(final String id) {
        database.remove(id);
    }



    @Override
    public void delete(final Offer entity) {

    }

    @Override
    public void deleteAllById(final Iterable<? extends String> strings) {

    }


    @Override
    public void deleteAll(final Iterable<? extends Offer> entities) {
    }

    @Override
    public void deleteAll() {
        database.clear();
    }

    @Override
    public <S extends Offer> List<S> saveAll(final Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<Offer> findAll() {
        return database.values().stream().toList();
    }

    @Override
    public List<Offer> findAllById(final Iterable<String> strings) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public <S extends Offer> S insert(final S entity) {
        return null;
    }

    @Override
    public <S extends Offer> List<S> insert(final Iterable<S> entities) {
        return List.of();
    }

    @Override
    public <S extends Offer> Optional<S> findOne(final Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Offer> List<S> findAll(final Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Offer> List<S> findAll(final Example<S> example, final Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Offer> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Offer> long count(final Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Offer> boolean exists(final Example<S> example) {
        return false;
    }

    @Override
    public <S extends Offer, R> R findBy(final Example<S> example, final Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Offer> findAll(final Sort sort) {
        return List.of();
    }

    @Override
    public Page<Offer> findAll(final Pageable pageable) {
        return null;
    }

    @Override
    public boolean existsByUrl(final String url) {
        return database.values().stream()
                .anyMatch(offer -> offer.getUrl().equals(url));
    }
}
