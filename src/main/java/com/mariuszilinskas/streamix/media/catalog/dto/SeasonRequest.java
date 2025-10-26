package com.mariuszilinskas.streamix.media.catalog.dto;

public record SeasonRequest(
        int seasonNumber,
        Double rating,
        String poster
) {}
