package com.mariuszilinskas.vsp.media.catalog.dto;

import java.time.LocalDate;

public record PersonRequest(
        String name,
        String description,
        LocalDate born,
        LocalDate died,
        String imageURL
) {}
