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

    @PostMapping
    public ResponseEntity<Series> createSeries(@Valid @RequestBody SeriesRequest request) {
        Series response = mediaAdminService.createSeries(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{mediaId}")
    @CacheEvict(value = "media", key = "#mediaId")
    public ResponseEntity<Series> updateSeriesById(
            @PathVariable String mediaId,
            @Valid @RequestBody SeriesRequest request
    ) {
        Series response = mediaAdminService.updateSeriesById(mediaId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{mediaId}")
    @CacheEvict(value = "media", key = "#mediaId")
    public ResponseEntity<Void> deleteSeriesById(@PathVariable String mediaId){
        mediaAdminService.deleteSeriesById(mediaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
