package com.mariuszilinskas.vsp.media.catalog.dto;

import com.mariuszilinskas.vsp.media.catalog.enums.Genre;
import com.mariuszilinskas.vsp.media.catalog.enums.Tag;
import com.mariuszilinskas.vsp.media.catalog.model.embedded.CastMember;
import com.mariuszilinskas.vsp.media.catalog.model.embedded.CrewMember;

import java.time.LocalDate;
import java.util.List;

public record MovieRequest(
        MediaRequest mediaAttributes,
        List<CrewMember> directors,
        List<CrewMember> writers,
        Integer duration
) {
    public String title() { return mediaAttributes.title(); }
    public String description() { return mediaAttributes.description(); }
    public String type() { return mediaAttributes.type(); }
    public LocalDate releaseDate() { return mediaAttributes.releaseDate(); }
    public List<String> releaseCountries() { return mediaAttributes.releaseCountries(); }
    public Double rating() { return mediaAttributes.rating(); }
    public List<Genre> genres() { return mediaAttributes.genres(); }
    public List<CastMember> cast() { return mediaAttributes.cast(); }
    public List<CrewMember> crew() { return mediaAttributes.crew(); }
    public List<String> availableCountries() { return mediaAttributes.availableCountries(); }
    public List<Tag> tags() { return mediaAttributes.tags(); }
    public String poster() { return mediaAttributes.poster(); }
    public String contentUrl() { return mediaAttributes.contentUrl(); }
}
