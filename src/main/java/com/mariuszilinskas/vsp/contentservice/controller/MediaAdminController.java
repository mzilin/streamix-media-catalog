package com.mariuszilinskas.vsp.contentservice.controller;

import com.mariuszilinskas.vsp.contentservice.dto.MovieRequest;
import com.mariuszilinskas.vsp.contentservice.dto.SeriesRequest;
import com.mariuszilinskas.vsp.contentservice.model.document.Movie;
import com.mariuszilinskas.vsp.contentservice.model.document.Series;
import com.mariuszilinskas.vsp.contentservice.service.MediaAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class provides REST APIs for handling CRUD operations related to media content
 * that are only accessible by system admins.
 *
 * @author Marius Zilinskas
 */
@RestController
@RequestMapping("/admin/media")
@RequiredArgsConstructor
public class MediaAdminController {

    private final MediaAdminService mediaAdminService;

    @PostMapping("/movie")
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody MovieRequest request) {
        Movie response = mediaAdminService.createMovie(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/movie/{mediaId}")
    @CacheEvict(value = "media", key = "#mediaId")
    public ResponseEntity<Movie> updateMovieById(
            @PathVariable String mediaId,
            @Valid @RequestBody MovieRequest request
    ) {
        Movie response = mediaAdminService.updateMovieById(mediaId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/movie/{mediaId}")
    @CacheEvict(value = "media", key = "#mediaId")
    public ResponseEntity<Void> deleteMovieById(@PathVariable String mediaId){
        mediaAdminService.deleteMovieById(mediaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // --------------------------------------

    @PostMapping("/series")
    public ResponseEntity<Series> createSeries(@Valid @RequestBody SeriesRequest request) {
        Series response = mediaAdminService.createSeries(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/series/{mediaId}")
    @CacheEvict(value = "media", key = "#mediaId")
    public ResponseEntity<Series> updateSeriesById(
            @PathVariable String mediaId,
            @Valid @RequestBody SeriesRequest request
    ) {
        Series response = mediaAdminService.updateSeriesById(mediaId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/series/{mediaId}")
    @CacheEvict(value = "media", key = "#mediaId")
    public ResponseEntity<Void> deleteSeriesById(@PathVariable String mediaId){
        mediaAdminService.deleteSeriesById(mediaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
