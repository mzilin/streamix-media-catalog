package com.mariuszilinskas.vsp.contentservice.model.document;

import com.mariuszilinskas.vsp.contentservice.enums.ContentType;
import com.mariuszilinskas.vsp.contentservice.enums.Genre;
import com.mariuszilinskas.vsp.contentservice.enums.Tag;
import com.mariuszilinskas.vsp.contentservice.model.embedded.CastMember;
import com.mariuszilinskas.vsp.contentservice.model.embedded.CrewMember;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Document(collection = "media")
public class Media {

    @MongoId
    private String id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("type")
    private ContentType type;

    @Field("releaseDate")
    private LocalDate releaseDate;

    @Field("releaseCountries")
    private List<String> releaseCountries;

    @Field("rating")
    private Double rating;

    @Field("genres")
    private List<Genre> genres;

    @Field("cast")
    private List<CastMember> cast;

    @Field("crew")
    private List<CrewMember> crew;

    @Field("availableCountries")
    private List<String> availableCountries;

    @Field("tags")
    private List<Tag> tags;

    @Field("poster")
    private String poster;

}
