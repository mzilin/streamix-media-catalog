package com.mariuszilinskas.vsp.contentservice.dto;

import com.mariuszilinskas.vsp.contentservice.enums.Genre;
import com.mariuszilinskas.vsp.contentservice.enums.Tag;
import com.mariuszilinskas.vsp.contentservice.model.embedded.CastMember;
import com.mariuszilinskas.vsp.contentservice.model.embedded.CrewMember;

import java.time.LocalDate;
import java.util.List;

public record MediaRequest(
        String title,
        String description,
        String type,
        LocalDate releaseDate,
        List<String> releaseCountries,
        Double rating,
        List<Genre> genres,
        List<CastMember> cast,
        List<CrewMember> crew,
        List<String> availableCountries,
        List<Tag> tags,
        String poster,
        String contentUrl
) {}
