package com.mariuszilinskas.vsp.media.catalog.dto;

import com.mariuszilinskas.vsp.media.catalog.enums.Genre;
import com.mariuszilinskas.vsp.media.catalog.enums.Tag;
import com.mariuszilinskas.vsp.media.catalog.model.embedded.CastMember;
import com.mariuszilinskas.vsp.media.catalog.model.embedded.CrewMember;

import java.time.LocalDate;
import java.util.List;

public record SeriesRequest(
        MediaRequest commonMediaAttributes,
        List<CrewMember> creators
) {
    public String title() { return commonMediaAttributes.title(); }
    public String description() { return commonMediaAttributes.description(); }
    public String type() { return commonMediaAttributes.type(); }
    public LocalDate releaseDate() { return commonMediaAttributes.releaseDate(); }
    public List<String> releaseCountries() { return commonMediaAttributes.releaseCountries(); }
    public Double rating() { return commonMediaAttributes.rating(); }
    public List<Genre> genres() { return commonMediaAttributes.genres(); }
    public List<CastMember> cast() { return commonMediaAttributes.cast(); }
    public List<CrewMember> crew() { return commonMediaAttributes.crew(); }
    public List<String> availableCountries() { return commonMediaAttributes.availableCountries(); }
    public List<Tag> tags() { return commonMediaAttributes.tags(); }
    public String poster() { return commonMediaAttributes.poster(); }
    public String contentUrl() { return commonMediaAttributes.contentUrl(); }
}
