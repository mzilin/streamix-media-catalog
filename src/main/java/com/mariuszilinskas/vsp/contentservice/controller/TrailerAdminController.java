package com.mariuszilinskas.vsp.contentservice.controller;

import com.mariuszilinskas.vsp.contentservice.dto.TrailerRequest;
import com.mariuszilinskas.vsp.contentservice.model.document.Trailer;
import com.mariuszilinskas.vsp.contentservice.service.TrailerAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class provides REST APIs for handling CRUD operations related to trailer content
 * that are only accessible by system admins.
 *
 * @author Marius Zilinskas
 */
@RestController
@RequestMapping("/admin/trailer")
@RequiredArgsConstructor
public class TrailerAdminController {

    private final TrailerAdminService trailerAdminService;

    @PostMapping("/{mediaId}")
    public ResponseEntity<Trailer> createTrailer(
            @PathVariable String mediaId,
            @Valid @RequestBody TrailerRequest request
    ){
        Trailer response = trailerAdminService.createTrailer(mediaId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{mediaId}/{trailerId}")
    public ResponseEntity<Trailer> updateTrailer(
            @PathVariable String trailerId,
            @PathVariable String mediaId,
            @Valid @RequestBody TrailerRequest request
    ){
        Trailer response = trailerAdminService.updateTrailer(trailerId, mediaId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{mediaId}/{trailerId}")
    public ResponseEntity<Void> deleteTrailer(
            @PathVariable String trailerId,
            @PathVariable String mediaId
    ){
        trailerAdminService.deleteTrailer(trailerId, mediaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
