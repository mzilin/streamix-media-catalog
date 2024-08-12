package com.mariuszilinskas.vsp.contentservice.model.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "episodes")
public class Episode {

    @MongoId
    private String id;

    @Field("seriesId")
    private String seriesId;

    @Field("seasonId")
    private String seasonId;

    @Field("episodeNumber")
    private int episodeNumber;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("releaseDate")
    private LocalDate releaseDate;

    @Field("rating")
    private Double rating = 0.0;

    @Field("duration")
    private Integer duration; // duration in minutes

    @Field("thumbnail")
    private String thumbnail;

    @Field("contentUrl")
    private String contentUrl;

}
