package com.joboffersapi.domain.offerCRUD;

import com.joboffersapi.domain.model.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Document(collection = "offers")
@Getter
@Setter
@Builder
class Offer extends BaseEntity {

    @MongoId(value = FieldType.OBJECT_ID)
    private Long id;

    private String title;
    private String company;
    private double salary;
    private String url;



}
