package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.dto.TrailerRequest;
import com.mariuszilinskas.vsp.contentservice.model.document.Trailer;

public interface TrailerAdminService {

    Trailer createTrailer(String mediaId, TrailerRequest request);

    Trailer updateTrailer(String id, String mediaId, TrailerRequest request);

    void deleteTrailer(String id, String mediaId);

    void deleteAllTrailersByMediaId(String mediaId);

}
