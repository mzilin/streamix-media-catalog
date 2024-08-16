package com.mariuszilinskas.vsp.contentservice.model.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "people")
public class Person {

    @MongoId
    private String id;

    @Indexed
    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("born")
    private LocalDate born;

    @Field("died")
    private LocalDate died;

    @Field("imageURL")
    private String imageURL;

}
