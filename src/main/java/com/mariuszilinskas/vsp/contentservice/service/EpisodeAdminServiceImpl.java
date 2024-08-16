package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.dto.EpisodeRequest;
import com.mariuszilinskas.vsp.contentservice.exception.ResourceNotFoundException;
import com.mariuszilinskas.vsp.contentservice.model.document.Episode;
import com.mariuszilinskas.vsp.contentservice.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing series episode content, accessible only by system admins.
 * This service handles series episode creation, updates and deletion.
 *
 * @author Marius Zilinskas
 */
@Service
@RequiredArgsConstructor
public class EpisodeAdminServiceImpl implements EpisodeAdminService {

    private static final Logger logger = LoggerFactory.getLogger(EpisodeAdminServiceImpl.class);
    private final EpisodeRepository episodeRepository;
    private final SeasonAdminService seasonAdminService;

    @Override
    @Transactional
    public Episode createEpisodeInSeason(String seriesId, String seasonId, EpisodeRequest request) {
        logger.info("Creating new Episode [number: '{}'] for Season [id: '{}']", request.episodeNumber(), seasonId);

        Episode newEpisode = populateNewEpisodeWithRequestData(seriesId, seasonId, request);
        int currentEpisodeCount = getEpisodeCountForSeason(seriesId, seasonId);
        seasonAdminService.updateSeasonEpisodeCount(seriesId, seasonId, currentEpisodeCount + 1);

        return newEpisode;
    }

    private Episode populateNewEpisodeWithRequestData(String seriesId, String seasonId, EpisodeRequest request) {
        Episode episode = new Episode();
        episode.setSeriesId(seriesId);
        episode.setSeasonId(seasonId);
        return applyEpisodeUpdates(episode, request);
    }

    @Override
    @Transactional
    public Episode updateEpisodeInSeason(String seriesId, String seasonId, String id, EpisodeRequest request) {
        logger.info("Updating new Episode [id: '{}'] for Season [id: '{}']", id, seasonId);

        Episode episode = findEpisodeBySeriesIdAndSeasonIdAndId(seriesId, seasonId, id);
        return applyEpisodeUpdates(episode, request);
    }

    private Episode applyEpisodeUpdates(Episode episode, EpisodeRequest request) {
        episode.setEpisodeNumber(request.episodeNumber());
        episode.setRating(request.rating());
        episode.setTitle(request.title());
        episode.setDescription(request.description());
        episode.setReleaseDate(request.releaseDate());
        episode.setRating(request.rating());
        episode.setDuration(request.duration());
        episode.setThumbnail(request.thumbnail());
        episode.setContentUrl(request.contentUrl());
        return episodeRepository.save(episode);
    }

    @Override
    @Transactional
    public void removeEpisodeFromSeason(String seriesId, String seasonId, String id) {
        logger.info("Removing Episode [id '{}'] from Season [id: '{}']", id, seasonId);

        int currentEpisodeCount = getEpisodeCountForSeason(seriesId, seasonId);
        seasonAdminService.updateSeasonEpisodeCount(seriesId, seasonId, currentEpisodeCount - 1);

        episodeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void removeAllEpisodesFromSeason(String seriesId, String seasonId) {
        logger.info("Removing all Episodes from Season [id: '{}']", seasonId);
        episodeRepository.deleteAllBySeriesIdAndSeasonId(seriesId, seasonId);
    }

    @Override
    @Transactional
    public void removeAllEpisodesFromSeries(String seriesId) {
        logger.info("Removing all Episodes from Series [id: '{}']", seriesId);
        episodeRepository.deleteAllBySeriesId(seriesId);
    }

    // --------------------------------------

    private Episode findEpisodeBySeriesIdAndSeasonIdAndId(String seriesId, String seasonId, String id) {
        return episodeRepository.findByIdAndSeriesIdAndSeasonId(id, seriesId, seasonId)
                .orElseThrow(() -> new ResourceNotFoundException(Episode.class, "id", id));
    }

    private int getEpisodeCountForSeason(String seriesId, String seasonId) {
        return episodeRepository.countBySeriesIdAndSeasonId(seriesId, seasonId);
    }

}
