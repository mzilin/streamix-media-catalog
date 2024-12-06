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

    @PutMapping("/{mediaId}")
    @CacheEvict(value = "media", key = "#mediaId")
    public ResponseEntity<Movie> updateMovieById(
            @PathVariable String mediaId,
            @Valid @RequestBody MovieRequest request
    ) {
        Movie response = mediaAdminService.updateMovieById(mediaId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{mediaId}")
    @CacheEvict(value = "media", key = "#mediaId")
    public ResponseEntity<Void> deleteMovieById(@PathVariable String mediaId){
        mediaAdminService.deleteMovieById(mediaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
