package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.dto.SeasonRequest;
import com.mariuszilinskas.vsp.contentservice.model.document.Season;

public interface SeasonAdminService {

    Season createSeasonForSeries(String seriesId, SeasonRequest request);

    Season updateSeasonInSeries(String seriesId, String id, SeasonRequest request);

    void deleteSeasonFromSeries(String seriesId, String id);

    void deleteAllSeasonsFromSeries(String seriesId);

}
