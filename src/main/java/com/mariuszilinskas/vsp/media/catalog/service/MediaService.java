package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.model.document.Media;

import java.util.List;

public interface MediaService {
    // TODO: RETRIEVES movie data including all cast, and trailers, and seasons???
    // TODO: separate for episode and season???

    Media getMediaById(String mediaId);

}
