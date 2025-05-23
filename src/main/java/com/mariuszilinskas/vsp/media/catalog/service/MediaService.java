package com.mariuszilinskas.vsp.media.catalog.service;

import com.mariuszilinskas.vsp.media.catalog.model.document.Media;

import java.util.List;

public interface MediaService {
    // TODO: RETRIEVES movie data including all cast, and trailers, and seasons???
    // TODO: separate for episode and season???

    Media getMediaById(String mediaId);

}
