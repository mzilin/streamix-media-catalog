package com.mariuszilinskas.vsp.contentservice.dto;

public record SeasonRequest(
        String seriesId,
        int seasonNumber,
        Double rating,
        String poster
) {}
