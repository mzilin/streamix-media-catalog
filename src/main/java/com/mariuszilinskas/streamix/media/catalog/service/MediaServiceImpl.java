package com.mariuszilinskas.streamix.media.catalog.service;

import com.mariuszilinskas.streamix.media.catalog.model.document.Media;
import com.mariuszilinskas.streamix.media.catalog.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private static final Logger logger = LoggerFactory.getLogger(MediaServiceImpl.class);
    private final MediaRepository mediaRepository;

    @Override
    public Media getMediaById(String mediaId) {
        logger.info("Fetching Media [id: '{}'", mediaId);

//        TODO: do I need to remove caching on episode and trailer updates as if they will be part of get media response??

        // 1. Get media data

        // 2. Get cast and crew

        // 3. Get all trailers

        // 4. If series, get seasons, episodes

        return null;
    }

}
