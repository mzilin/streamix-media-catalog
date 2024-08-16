package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.dto.TrailerRequest;
import com.mariuszilinskas.vsp.contentservice.model.document.Trailer;

public interface TrailerAdminService {

    Trailer createTrailer(TrailerRequest request);

    Trailer updateTrailer(String id, TrailerRequest request);

    void deleteTrailer(String id);

}
