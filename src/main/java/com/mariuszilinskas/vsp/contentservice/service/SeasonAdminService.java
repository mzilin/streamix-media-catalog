package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.dto.SeasonRequest;
import com.mariuszilinskas.vsp.contentservice.model.document.Season;

public interface SeasonAdminService {

    Season createSeasonForSeries(String seriesId, SeasonRequest request);

    Season updateSeasonInSeries(String seriesId, String id, SeasonRequest request);

    int updateSeasonEpisodeCount(String seriesId, String id, int episodeCount);

    void removeSeasonFromSeries(String seriesId, String id);

    void removeAllSeasonsFromSeries(String seriesId);

}
