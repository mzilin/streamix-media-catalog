package com.mariuszilinskas.vsp.contentservice.model.embedded;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class CastMember {

    @Field("id")
    private String id;

    @Field("name")
    private String name;

    @Field("character")
    private String character;

}
