package com.mariuszilinskas.streamix.media.catalog.service;

import com.mariuszilinskas.streamix.media.catalog.dto.TrailerRequest;
import com.mariuszilinskas.streamix.media.catalog.model.document.Trailer;

public interface TrailerAdminService {

    Trailer createTrailer(String mediaId, TrailerRequest request);

    Trailer updateTrailer(String id, String mediaId, TrailerRequest request);

    void deleteTrailer(String id, String mediaId);

    void deleteAllTrailersByMediaId(String mediaId);

}
