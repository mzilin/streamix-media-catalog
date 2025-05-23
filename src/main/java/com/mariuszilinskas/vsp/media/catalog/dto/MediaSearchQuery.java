package com.mariuszilinskas.vsp.contentservice.dto;

import com.mariuszilinskas.vsp.contentservice.enums.ContentType;
import com.mariuszilinskas.vsp.contentservice.enums.Genre;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

/**
 * This class represents a search request params for GET /media/search.
 *
 * @author Marius Zilinskas
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MediaSearchQuery {

    @Size(max = 100, message = "Search text should be max 100 characters")
    private String q = null;    // Search text

    private ContentType type;

    private LocalDate date = null; // release date

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double minRating = null;

    private Genre genre = null;

    @Size(max = 50, message = "Country should be max 50 characters")
    private String country = null;

    @Pattern(regexp = "^[a-zA-Z]+(,[a-zA-Z]+)*$", message = "Tags should be letter words separated by commas")
    @Size(max = 100, message = "Tags should be max 100 characters")
    private String tags = null;

    @Min(value = 1, message = "Page should be greater than 0")
    private int page = 1;   // Current page

    @Min(value = 10, message = "Size should be more or equal to 10")
    @Max(value = 100, message = "Size should be less than or equal to 100")
    private int size = 25;   // Number of records per page

}
