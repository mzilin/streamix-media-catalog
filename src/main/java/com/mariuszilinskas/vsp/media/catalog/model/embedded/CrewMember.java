package com.mariuszilinskas.vsp.media.catalog.model.embedded;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class CrewMember {

    @Field("id")
    private String id;

    @Field("name")
    private String name;

    @Field("position")
    private String position;

}
