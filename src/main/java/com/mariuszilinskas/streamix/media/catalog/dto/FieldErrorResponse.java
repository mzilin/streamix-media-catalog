package com.mariuszilinskas.streamix.media.catalog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mariuszilinskas.streamix.media.catalog.util.CatalogUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * This class represents the structure for field error responses of the application.
 *
 * @author Marius Zilinskas
 */
@Getter
@Setter
public class FieldErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CatalogUtils.TIMESTAMP_FORMAT)
    private ZonedDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final Map<String, String> fieldErrors;

    /**
     * Construct an FieldErrorResponse with specified error messages.
     * The timestamp is automatically set to the current time.
     *
     * @param fieldErrors a map of field errors
     * @param status the HTTP status code
     * @param error the type of the error
     */
    public FieldErrorResponse(Map<String, String> fieldErrors, int status, String error) {
        this.fieldErrors = fieldErrors;
        this.status = status;
        this.message = "Invalid input data. Please correct the errors and try again.";
        this.error = error;
        timestamp = ZonedDateTime.now();
    }

}
