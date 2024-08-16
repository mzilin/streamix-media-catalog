package com.mariuszilinskas.vsp.contentservice.model.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document(collection = "seasons")
public class Season {

    @MongoId
    private String id;

    @Field("seriesId")
    private String seriesId;

    @Field("seasonNumber")
    private int seasonNumber;

    @Field("rating")
    private Double rating = 0.0;

    @Field("episodeCount")
    private int episodeCount = 0;

    @Field("poster")
    private String poster;

}
