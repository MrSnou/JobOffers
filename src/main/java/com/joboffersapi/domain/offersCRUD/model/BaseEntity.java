package com.joboffersapi.domain.offersCRUD.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public abstract class BaseEntity {

    @Id
    private final UUID uuid = UUID.randomUUID();

    @CreatedDate
    private Instant createdOn;

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final BaseEntity that = (BaseEntity) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }
}
