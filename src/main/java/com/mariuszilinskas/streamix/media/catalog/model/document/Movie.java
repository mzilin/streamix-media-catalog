package com.mariuszilinskas.streamix.media.catalog.model.document;

import com.mariuszilinskas.streamix.media.catalog.model.embedded.CrewMember;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@Document
public class Movie extends Media {

    @Field("duration")
    private Integer duration;   // duration in minutes

    @Field("directors")
    private List<CrewMember> directors;

    @Field("writers")
    private List<CrewMember> writers;

    @Field("contentUrl")
    private String contentUrl;

}
