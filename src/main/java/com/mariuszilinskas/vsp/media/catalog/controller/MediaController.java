package com.mariuszilinskas.vsp.media.catalog.controller;

import com.mariuszilinskas.vsp.media.catalog.model.document.Media;
import com.mariuszilinskas.vsp.media.catalog.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class provides REST APIs for retrieving media content that is publicly accessible.
 *
 * @author Marius Zilinskas
 */
@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @GetMapping("/{mediaId}")
    @Cacheable(value = "media", key = "#mediaId")
    public ResponseEntity<Media> getMediaById(@PathVariable String mediaId) {
        return null;
    }

}
