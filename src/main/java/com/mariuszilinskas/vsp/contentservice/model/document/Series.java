package com.mariuszilinskas.vsp.contentservice.model.document;

import com.mariuszilinskas.vsp.contentservice.model.embedded.CrewMember;
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

    @Field("seasonCount")
    private int seasonCount = 0;

}
