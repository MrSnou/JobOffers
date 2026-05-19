package com.joboffersapi.domain.offersCRUD.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.UUID;

public abstract class BaseEntity {

    @Id
    private final UUID uuid = UUID.randomUUID();

    @CreatedDate
    private Instant createdOn;


}
