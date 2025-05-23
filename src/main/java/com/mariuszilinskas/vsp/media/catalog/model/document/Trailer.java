package com.mariuszilinskas.vsp.contentservice.model.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document(collection = "trailers")
public class Trailer {

    @MongoId
    private String id;

    @Field("mediaId")
    private String mediaId;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("duration")
    private Integer duration; // duration in seconds

    @Field("thumbnail")
    private String thumbnail;

    @Field("contentUrl")
    private String contentUrl;

}
