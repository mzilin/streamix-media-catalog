package com.mariuszilinskas.vsp.contentservice.model.document;

import com.mariuszilinskas.vsp.contentservice.enums.ContentType;
import com.mariuszilinskas.vsp.contentservice.enums.Genre;
import com.mariuszilinskas.vsp.contentservice.enums.Tag;
import com.mariuszilinskas.vsp.contentservice.model.embedded.CastMember;
import com.mariuszilinskas.vsp.contentservice.model.embedded.CrewMember;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Document(collection = "media")
@CompoundIndexes({@CompoundIndex(name = "type_releaseDate_idx", def = "{'type' : 1, 'releaseDate' : -1}")})
public class Media {

    @MongoId
    private String id;

    @Indexed
    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Indexed
    @Field("type")
    private ContentType type;

    @Indexed
    @Field("releaseDate")
    private LocalDate releaseDate;

    @Field("releaseCountries")
    private List<String> releaseCountries;

    @Indexed
    @Field("rating")
    private Double rating = 0.0;

    @Indexed
    @Field("genres")
    private List<Genre> genres;

    @Field("cast")
    private List<CastMember> cast;

    @Field("crew")
    private List<CrewMember> crew;

    @Indexed
    @Field("availableCountries")
    private List<String> availableCountries;

    @Indexed
    @Field("tags")
    private List<Tag> tags;

    @Field("poster")
    private String poster;

}
