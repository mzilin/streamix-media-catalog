package com.mariuszilinskas.vsp.contentservice.controller;

import com.mariuszilinskas.vsp.contentservice.dto.*;
import com.mariuszilinskas.vsp.contentservice.model.document.*;
import com.mariuszilinskas.vsp.contentservice.service.EpisodeAdminService;
import com.mariuszilinskas.vsp.contentservice.service.MediaAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class provides REST APIs for handling CRUD operations related to series content
 * that are only accessible by system admins.
 *
 * @author Marius Zilinskas
 */
@RestController
@RequestMapping("/admin/media/series")
@RequiredArgsConstructor
public class SeriesAdminController {

    private final MediaAdminService mediaAdminService;
    private final EpisodeAdminService episodeAdminService;

    @PostMapping
    public ResponseEntity<Series> createSeries(@Valid @RequestBody SeriesRequest request) {
        Series response = mediaAdminService.createSeries(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{seriesId}")
    @CacheEvict(value = "media", key = "#seriesId")
    public ResponseEntity<Series> updateSeriesById(
            @PathVariable String seriesId,
            @Valid @RequestBody SeriesRequest request
    ) {
        Series response = mediaAdminService.updateSeriesById(seriesId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{seriesId}")
    @CacheEvict(value = "media", key = "#seriesId")
    public ResponseEntity<Void> deleteSeriesById(@PathVariable String seriesId){
        mediaAdminService.deleteSeriesById(seriesId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{seriesId}/episodes")
    public ResponseEntity<Void> deleteAllEpisodesFromSeries(@PathVariable String seriesId) {
        episodeAdminService.deleteAllEpisodesFromSeries(seriesId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
