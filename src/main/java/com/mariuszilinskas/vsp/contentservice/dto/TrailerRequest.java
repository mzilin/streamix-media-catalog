package com.mariuszilinskas.vsp.contentservice.dto;

public record TrailerRequest(
        String mediaId,
        String title,
        String description,
        Integer duration,
        String thumbnail,
        String contentUrl
) {}
