package com.mariuszilinskas.vsp.media.catalog.dto;

public record SeasonRequest(
        int seasonNumber,
        Double rating,
        String poster
) {}
