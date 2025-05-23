package com.mariuszilinskas.vsp.media.catalog.controller;

import com.mariuszilinskas.vsp.media.catalog.dto.*;
import com.mariuszilinskas.vsp.media.catalog.model.document.*;
import com.mariuszilinskas.vsp.media.catalog.service.EpisodeAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class provides REST APIs for handling CRUD operations related to episode content
 * that are only accessible by system admins.
 *
 * @author Marius Zilinskas
 */
@RestController
@RequestMapping("/admin/media/series/{seriesId}/seasons/{seasonId}/episodes")
@RequiredArgsConstructor
public class EpisodeAdminController {

    private final EpisodeAdminService episodeAdminService;



    @PostMapping
    public ResponseEntity<Episode> createEpisodeInEpisode(
            @PathVariable String seriesId,
            @PathVariable String seasonId,
            @Valid @RequestBody EpisodeRequest request
    ) {
        Episode response = episodeAdminService.createEpisodeInSeason(seriesId, seasonId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{episodeId}")
    public ResponseEntity<Episode> updateEpisodeInSeason(
            @PathVariable String seriesId,
            @PathVariable String seasonId,
            @PathVariable String episodeId,
            @Valid @RequestBody EpisodeRequest request
    ) {
        Episode response = episodeAdminService.updateEpisodeInSeason(seriesId, seasonId, episodeId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{episodeId}")
    public ResponseEntity<Void> deleteEpisodeFromSeason(
            @PathVariable String seriesId,
            @PathVariable String seasonId,
            @PathVariable String episodeId
    ) {
        episodeAdminService.deleteEpisodeFromSeason(seriesId, seasonId, episodeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllEpisodesFromSeason(
            @PathVariable String seriesId,
            @PathVariable String seasonId
    ) {
        episodeAdminService.deleteAllEpisodesFromSeason(seriesId, seasonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
