package com.mariuszilinskas.vsp.media.catalog.dto;

import java.time.LocalDate;
import java.util.List;

public record MediaESRequest(
        String contentId,
        String title,
        String type,
        LocalDate releaseDate,
        List<String> genres,
        List<String> castAndCrew,
        List<String> tags,
        String poster
) {}
