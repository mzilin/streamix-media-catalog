package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.dto.TrailerRequest;
import com.mariuszilinskas.vsp.contentservice.exception.ResourceNotFoundException;
import com.mariuszilinskas.vsp.contentservice.model.document.Trailer;
import com.mariuszilinskas.vsp.contentservice.repository.TrailerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing trailer content, accessible only by system admins.
 * This service handles trailer content creation, updates and deletion.
 *
 * @author Marius Zilinskas
 */
@Service
@RequiredArgsConstructor
public class TrailerAdminServiceImpl implements TrailerAdminService {

    private static final Logger logger = LoggerFactory.getLogger(TrailerAdminServiceImpl.class);
    private final TrailerRepository trailerRepository;

    @Override
    @Transactional
    public Trailer createTrailer(String mediaId, TrailerRequest request) {
        logger.info("Creating new Trailer for Media [id: '{}']", mediaId);
        return populateNewTrailerWithRequestData(mediaId, request);
    }

    private Trailer populateNewTrailerWithRequestData(String mediaId, TrailerRequest request) {
        Trailer trailer = new Trailer();
        trailer.setMediaId(mediaId);
        return applyTrailerUpdates(trailer, request);
    }

    @Override
    @Transactional
    public Trailer updateTrailer(String id, String mediaId, TrailerRequest request) {
        logger.info("Updating Trailer [id: '{}']", id);
        Trailer trailer = findTrailerByIdAndMediaId(id, mediaId);
        return applyTrailerUpdates(trailer, request);
    }

    private Trailer applyTrailerUpdates(Trailer trailer, TrailerRequest request) {
        trailer.setTitle(request.title());
        trailer.setDescription(request.description());
        trailer.setDuration(request.duration());
        trailer.setThumbnail(request.thumbnail());
        trailer.setContentUrl(request.contentUrl());
        return trailerRepository.save(trailer);
    }

    @Override
    @Transactional
    public void deleteTrailer(String id, String mediaId) {
        logger.info("Deleting Trailer [id: '{}']", id);
        Trailer trailer = findTrailerByIdAndMediaId(id, mediaId);
        trailerRepository.delete(trailer);
    }

    // --------------------------------------

    private Trailer findTrailerByIdAndMediaId(String id, String mediaId) {
        return trailerRepository.findByIdAndMediaId(id, mediaId)
                .orElseThrow(() -> new ResourceNotFoundException(Trailer.class, "id", id));
    }

}
