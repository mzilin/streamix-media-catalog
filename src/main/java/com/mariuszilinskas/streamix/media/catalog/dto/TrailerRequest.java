package com.mariuszilinskas.streamix.media.catalog.dto;

public record TrailerRequest(
        String title,
        String description,
        Integer duration,
        String thumbnail,
        String contentUrl
) {}
