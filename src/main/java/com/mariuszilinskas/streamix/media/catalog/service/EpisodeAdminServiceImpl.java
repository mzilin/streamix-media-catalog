package com.mariuszilinskas.streamix.media.catalog.service;

import com.mariuszilinskas.streamix.media.catalog.dto.EpisodeRequest;
import com.mariuszilinskas.streamix.media.catalog.exception.EntityExistsException;
import com.mariuszilinskas.streamix.media.catalog.exception.ResourceNotFoundException;
import com.mariuszilinskas.streamix.media.catalog.model.document.Episode;
import com.mariuszilinskas.streamix.media.catalog.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Override
    @Transactional
    public Episode createEpisodeInSeason(String seriesId, String seasonId, EpisodeRequest request) {
        logger.info("Creating new Episode [number: '{}'] for Season [id: '{}']", request.episodeNumber(), seasonId);
        checkEpisodeNumberExists(seriesId, seasonId, request.episodeNumber());
        return populateNewEpisodeWithRequestData(seriesId, seasonId, request);
    }

    private void checkEpisodeNumberExists(String seriesId, String seasonId, int episodeNumber) {
        if (episodeRepository.existsBySeriesIdAndSeasonIdAndEpisodeNumber(seriesId, seasonId, episodeNumber)) {
            throw new EntityExistsException(Episode.class, "episodeNumber", episodeNumber);
        }
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
        checkEpisodeNumberExistsExcludeId(seriesId, seasonId, request.episodeNumber(), id);

        return applyEpisodeUpdates(episode, request);
    }

    private void checkEpisodeNumberExistsExcludeId(String seriesId, String seasonId, int episodeNumber, String id) {
        Optional<Episode> episode = episodeRepository.findBySeriesIdAndSeasonIdAndEpisodeNumber(seriesId, seasonId, episodeNumber);
        if (episode.isPresent() && !episode.get().getId().equals(id)) {
            throw new EntityExistsException(Episode.class, "episodeNumber", episodeNumber);
        }
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
    public void deleteEpisodeFromSeason(String seriesId, String seasonId, String id) {
        logger.info("Deleting Episode [id '{}'] from Season [id: '{}']", id, seasonId);
        Episode episode = findEpisodeBySeriesIdAndSeasonIdAndId(seriesId, seasonId, id);
        episodeRepository.delete(episode);
    }

    private Episode findEpisodeBySeriesIdAndSeasonIdAndId(String seriesId, String seasonId, String id) {
        return episodeRepository.findByIdAndSeriesIdAndSeasonId(id, seriesId, seasonId)
                .orElseThrow(() -> new ResourceNotFoundException(Episode.class, "id", id));
    }

    @Override
    @Transactional
    public void deleteAllEpisodesFromSeason(String seriesId, String seasonId) {
        logger.info("Deleting all Episodes from Season [id: '{}']", seasonId);
        episodeRepository.deleteAllBySeriesIdAndSeasonId(seriesId, seasonId);
    }

    @Override
    @Transactional
    public void deleteAllEpisodesFromSeries(String seriesId) {
        logger.info("Deleting all Episodes from Series [id: '{}']", seriesId);
        episodeRepository.deleteAllBySeriesId(seriesId);
    }

}
