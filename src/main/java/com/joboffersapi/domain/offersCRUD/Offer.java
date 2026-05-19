package com.joboffersapi.domain.offersCRUD;

import com.joboffersapi.domain.offersCRUD.model.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.net.URL;

@Document(collection = "offers")
@Getter
@Setter
@Builder
class Offer extends BaseEntity {

    @MongoId(value = FieldType.OBJECT_ID)
    private Long  id;
    @Field("title")
    private String title;
    @Field("description")
    private String description;
    @Field("salary")
    private double salary;
    @Field("url")
    private URL url;



}
