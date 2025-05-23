package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.dto.EpisodeRequest;
import com.mariuszilinskas.vsp.contentservice.model.document.Episode;

public interface EpisodeAdminService {

    Episode createEpisodeInSeason(String seriesId, String seasonId, EpisodeRequest request);

    Episode updateEpisodeInSeason(String seriesId, String seasonId, String id, EpisodeRequest request);

    void deleteEpisodeFromSeason(String seriesId, String seasonId, String id);

    void deleteAllEpisodesFromSeason(String seriesId, String seasonId);

    void deleteAllEpisodesFromSeries(String seriesId);

}
