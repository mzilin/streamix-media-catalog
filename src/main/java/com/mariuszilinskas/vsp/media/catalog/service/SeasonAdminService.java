package com.mariuszilinskas.vsp.media.catalog.service;

import com.mariuszilinskas.vsp.media.catalog.dto.SeasonRequest;
import com.mariuszilinskas.vsp.media.catalog.model.document.Season;

public interface SeasonAdminService {

    Season createSeasonForSeries(String seriesId, SeasonRequest request);

    Season updateSeasonInSeries(String seriesId, String id, SeasonRequest request);

    void deleteSeasonFromSeries(String seriesId, String id);

    void deleteAllSeasonsFromSeries(String seriesId);

}
