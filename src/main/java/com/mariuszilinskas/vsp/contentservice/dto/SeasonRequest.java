package com.mariuszilinskas.vsp.contentservice.dto;

public record SeasonRequest(
        int seasonNumber,
        Double rating,
        String poster
) {}
