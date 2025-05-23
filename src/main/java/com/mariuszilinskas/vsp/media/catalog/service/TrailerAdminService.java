package com.mariuszilinskas.vsp.media.catalog.service;

import com.mariuszilinskas.vsp.media.catalog.dto.TrailerRequest;
import com.mariuszilinskas.vsp.media.catalog.model.document.Trailer;

public interface TrailerAdminService {

    Trailer createTrailer(String mediaId, TrailerRequest request);

    Trailer updateTrailer(String id, String mediaId, TrailerRequest request);

    void deleteTrailer(String id, String mediaId);

    void deleteAllTrailersByMediaId(String mediaId);

}
