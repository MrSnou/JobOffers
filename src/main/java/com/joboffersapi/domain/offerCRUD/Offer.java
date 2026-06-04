package com.joboffersapi.domain.offerCRUD;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Document(collection = "offers")
@Getter
@Setter
@Builder
class Offer  {

    @MongoId(value = FieldType.OBJECT_ID)
    private String id;

    private String title;
    private String company;
    private String salary;
    @Indexed(unique = true)
    private String url;
    private String source;
    private boolean salary_estimated;



}
