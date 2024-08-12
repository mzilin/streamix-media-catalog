package com.mariuszilinskas.vsp.contentservice.dto;

import java.time.LocalDate;

public record EpisodeRequest(
        String seriesId,
        String seasonId,
        int episodeNumber,
        String title,
        String description,
        LocalDate releaseDate,
        Double rating,
        Integer duration, // duration in minutes
        String thumbnail,
        String contentUrl
) {}
