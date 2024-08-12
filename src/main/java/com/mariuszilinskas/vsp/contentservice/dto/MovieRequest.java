package com.mariuszilinskas.vsp.contentservice.dto;

import com.mariuszilinskas.vsp.contentservice.enums.Genre;
import com.mariuszilinskas.vsp.contentservice.enums.Tag;
import com.mariuszilinskas.vsp.contentservice.model.embedded.CastMember;
import com.mariuszilinskas.vsp.contentservice.model.embedded.CrewMember;

import java.time.LocalDate;
import java.util.List;

public record MovieRequest(
        String title,
        String description,
        String type,
        LocalDate releaseDate,
        List<String> releaseCountries,
        Double rating,
        List<Genre> genres,
        List<CastMember> cast,
        List<CrewMember> directors,
        List<CrewMember> writers,
        List<CrewMember> crew,
        List<String> availableCountries,
        Integer duration,
        List<Tag> tags,
        String poster,
        String contentUrl
) {}
