package com.mariuszilinskas.vsp.media.catalog.controller;

import com.mariuszilinskas.vsp.media.catalog.dto.*;
import com.mariuszilinskas.vsp.media.catalog.model.document.*;
import com.mariuszilinskas.vsp.media.catalog.service.SeasonAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class provides REST APIs for handling CRUD operations related to season content
 * that are only accessible by system admins.
 *
 * @author Marius Zilinskas
 */
@RestController
@RequestMapping("/admin/media/series/{seriesId}/seasons")
@RequiredArgsConstructor
public class SeasonAdminController {

    private final SeasonAdminService seasonAdminService;

    @PostMapping
    public ResponseEntity<Season> createSeasonForSeries(
            @PathVariable String seriesId,
            @Valid @RequestBody SeasonRequest request
    ) {
        Season response = seasonAdminService.createSeasonForSeries(seriesId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{seasonId}")
    public ResponseEntity<Season> updateSeasonInSeries(
            @PathVariable String seriesId,
            @PathVariable String seasonId,
            @Valid @RequestBody SeasonRequest request
    ) {
        Season response = seasonAdminService.updateSeasonInSeries(seriesId, seasonId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{seasonId}")
    public ResponseEntity<Void> deleteSeasonFromSeries(
            @PathVariable String seriesId,
            @PathVariable String seasonId
    ) {
        seasonAdminService.deleteSeasonFromSeries(seriesId, seasonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllSeasonsFromSeries(@PathVariable String seriesId) {
        seasonAdminService.deleteAllSeasonsFromSeries(seriesId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
