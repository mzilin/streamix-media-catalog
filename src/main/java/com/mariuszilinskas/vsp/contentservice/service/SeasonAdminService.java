package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.dto.SeasonRequest;
import com.mariuszilinskas.vsp.contentservice.model.document.Season;

public interface SeasonAdminService {

    Season createSeasonForSeries(String seriesId, SeasonRequest request);

    Season updateSeasonInSeries(String seriesId, int seasonNumber, SeasonRequest request);

    Season updateSeasonEpisodeCount(String seriesId, int seasonNumber, int episodeCount);

    void removeSeasonFromSeries(String seriesId, int seasonNumber);

    void removeAllSeasonsFromSeries(String seriesId);

}
