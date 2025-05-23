package com.mariuszilinskas.vsp.media.catalog.dto;

public record TrailerRequest(
        String title,
        String description,
        Integer duration,
        String thumbnail,
        String contentUrl
) {}
