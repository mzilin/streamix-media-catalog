package com.mariuszilinskas.vsp.media.catalog.model.document;

import com.mariuszilinskas.vsp.media.catalog.model.embedded.CrewMember;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@Document
public class Series extends Media {

    @Field("creators")
    private List<CrewMember> creators;

}
