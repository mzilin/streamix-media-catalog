package com.mariuszilinskas.vsp.contentservice.controller;

import com.mariuszilinskas.vsp.contentservice.dto.EpisodeRequest;
import com.mariuszilinskas.vsp.contentservice.dto.MovieRequest;
import com.mariuszilinskas.vsp.contentservice.dto.SeasonRequest;
import com.mariuszilinskas.vsp.contentservice.dto.SeriesRequest;
import com.mariuszilinskas.vsp.contentservice.model.document.Episode;
import com.mariuszilinskas.vsp.contentservice.model.document.Movie;
import com.mariuszilinskas.vsp.contentservice.model.document.Season;
import com.mariuszilinskas.vsp.contentservice.model.document.Series;
import com.mariuszilinskas.vsp.contentservice.service.EpisodeAdminService;
import com.mariuszilinskas.vsp.contentservice.service.MediaAdminService;
import com.mariuszilinskas.vsp.contentservice.service.SeasonAdminService;
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
    private final SeasonAdminService seasonAdminService;
    private final EpisodeAdminService episodeAdminService;

    @PostMapping("/movies")
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody MovieRequest request) {
        Movie response = mediaAdminService.createMovie(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/movies/{mediaId}")
    @CacheEvict(value = "media", key = "#mediaId")
    public ResponseEntity<Movie> updateMovieById(
            @PathVariable String mediaId,
            @Valid @RequestBody MovieRequest request
    ) {
        Movie response = mediaAdminService.updateMovieById(mediaId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/movies/{mediaId}")
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

    // --------------------------------------

    @PostMapping("/series/{seriesId}/seasons")
    public ResponseEntity<Season> createSeasonForSeries(
            @PathVariable String seriesId,
            @Valid @RequestBody SeasonRequest request
    ) {
        Season response = seasonAdminService.createSeasonForSeries(seriesId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/series/{seriesId}/seasons/{seasonId}")
    public ResponseEntity<Season> updateSeasonInSeries(
            @PathVariable String seriesId,
            @PathVariable String seasonId,
            @Valid @RequestBody SeasonRequest request
    ) {
        Season response = seasonAdminService.updateSeasonInSeries(seriesId, seasonId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/series/{seriesId}/seasons/{seasonId}")
    public ResponseEntity<Void> deleteSeasonFromSeries(
            @PathVariable String seriesId,
            @PathVariable String seasonId
    ) {
        seasonAdminService.deleteSeasonFromSeries(seriesId, seasonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/series/{seriesId}/seasons")
    public ResponseEntity<Void> deleteAllSeasonsFromSeries(@PathVariable String seriesId) {
        seasonAdminService.deleteAllSeasonsFromSeries(seriesId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // --------------------------------------

    @PostMapping("/series/{seriesId}/seasons/{seasonId}/episodes")
    public ResponseEntity<Episode> createEpisodeInEpisode(
            @PathVariable String seriesId,
            @PathVariable String seasonId,
            @Valid @RequestBody EpisodeRequest request
    ) {
        Episode response = episodeAdminService.createEpisodeInSeason(seriesId, seasonId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/series/{seriesId}/seasons/{seasonId}/episodes/{episodeId}")
    public ResponseEntity<Episode> updateEpisodeInSeason(
            @PathVariable String seriesId,
            @PathVariable String seasonId,
            @PathVariable String episodeId,
            @Valid @RequestBody EpisodeRequest request
    ) {
        Episode response = episodeAdminService.updateEpisodeInSeason(seriesId, seasonId, episodeId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/series/{seriesId}/seasons/{seasonId}/episodes/{episodeId}")
    public ResponseEntity<Void> deleteEpisodeFromSeason(
            @PathVariable String seriesId,
            @PathVariable String seasonId,
            @PathVariable String episodeId
    ) {
        episodeAdminService.deleteEpisodeFromSeason(seriesId, seasonId, episodeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/series/{seriesId}/seasons/{seasonId}/episodes")
    public ResponseEntity<Void> deleteAllEpisodesFromSeason(
            @PathVariable String seriesId,
            @PathVariable String seasonId
    ) {
        episodeAdminService.deleteAllEpisodesFromSeason(seriesId, seasonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/series/{seriesId}/episodes")
    public ResponseEntity<Void> deleteAllEpisodesFromSeries(@PathVariable String seriesId) {
        episodeAdminService.deleteAllEpisodesFromSeries(seriesId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
