package com.mariuszilinskas.vsp.contentservice.controller;

import com.mariuszilinskas.vsp.contentservice.dto.*;
import com.mariuszilinskas.vsp.contentservice.model.document.*;
import com.mariuszilinskas.vsp.contentservice.service.MediaAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class provides REST APIs for handling CRUD operations related to movies content
 * that are only accessible by system admins.
 *
 * @author Marius Zilinskas
 */
@RestController
@RequestMapping("/admin/media/movies")
@RequiredArgsConstructor
public class MovieAdminController {

    private final MediaAdminService mediaAdminService;

    @PostMapping
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody MovieRequest request) {
        Movie response = mediaAdminService.createMovie(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{movieId}")
    @CacheEvict(value = "media", key = "#movieId")
    public ResponseEntity<Movie> updateMovieById(
            @PathVariable String movieId,
            @Valid @RequestBody MovieRequest request
    ) {
        Movie response = mediaAdminService.updateMovieById(movieId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{movieId}")
    @CacheEvict(value = "media", key = "#movieId")
    public ResponseEntity<Void> deleteMovieById(@PathVariable String movieId){
        mediaAdminService.deleteMovieById(movieId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
