package com.mariuszilinskas.vsp.contentservice.dto;

public record TrailerRequest(
        String title,
        String description,
        Integer duration,
        String thumbnail,
        String contentUrl
) {}
