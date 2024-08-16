package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.dto.SeasonRequest;
import com.mariuszilinskas.vsp.contentservice.exception.ResourceNotFoundException;
import com.mariuszilinskas.vsp.contentservice.model.document.Season;
import com.mariuszilinskas.vsp.contentservice.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing series season content, accessible only by system admins.
 * This service handles series season creation, updates and deletion.
 *
 * @author Marius Zilinskas
 */
@Service
@RequiredArgsConstructor
public class SeasonAdminServiceImpl implements SeasonAdminService {

    private static final Logger logger = LoggerFactory.getLogger(SeasonAdminServiceImpl.class);
    private final SeasonRepository seasonRepository;
    private final MediaAdminService mediaAdminService;
    private final EpisodeAdminService episodeAdminService;

    @Override
    @Transactional
    public Season createSeasonForSeries(String seriesId, SeasonRequest request) {
        logger.info("Creating new Season [number: '{}'] for Series [id: '{}']", request.seasonNumber(), seriesId);

        Season newSeason = populateNewSeasonWithRequestData(request);
        newSeason.setSeriesId(seriesId);

        int currentSeasonCount = getSeasonCountForSeries(seriesId);
        mediaAdminService.updateSeriesSeasonCount(seriesId, currentSeasonCount + 1);

        return newSeason;
    }

    private Season populateNewSeasonWithRequestData(SeasonRequest request) {
        Season season = new Season();
        return applySeasonUpdates(season, request);
    }

    @Override
    @Transactional
    public Season updateSeasonInSeries(String seriesId, int seasonNumber, SeasonRequest request) {
        logger.info("Updating new Season [number: '{}'] for Series [id: '{}']", request.seasonNumber(), seriesId);

        Season season = findSeasonBySeriesIdAndSeasonNumber(seriesId, seasonNumber);
        return applySeasonUpdates(season, request);
    }

    private Season applySeasonUpdates(Season season, SeasonRequest request) {
        season.setSeasonNumber(request.seasonNumber());
        season.setRating(request.rating());
        season.setPoster(request.poster());
        return seasonRepository.save(season);
    }

    @Override
    @Transactional
    public Season updateSeasonEpisodeCount(String seriesId, int seasonNumber, int episodeCount) {
        logger.info("Updating Series [id: '{}'] Season [number '{}'] episode count to: '{}'", seriesId, seasonNumber, episodeCount);

        Season season = findSeasonBySeriesIdAndSeasonNumber(seriesId, seasonNumber);
        season.setEpisodeCount(episodeCount);

        return seasonRepository.save(season);
    }

    @Override
    @Transactional
    public void removeSeasonFromSeries(String seriesId, int seasonNumber) {
        logger.info("Removing Season [number '{}'] from Series [id: '{}']", seasonNumber, seriesId);

        int currentSeasonCount = getSeasonCountForSeries(seriesId);
        mediaAdminService.updateSeriesSeasonCount(seriesId, currentSeasonCount - 1);

        episodeAdminService.removeAllEpisodesFromSeason(seriesId, seasonNumber);

    }

    @Override
    @Transactional
    public void removeAllSeasonsFromSeries(String seriesId) {
        logger.info("Removing all Seasons from Series [id: '{}']", seriesId);

        seasonRepository.deleteBySeriesId(seriesId);
        episodeAdminService.removeAllEpisodesFromSeries(seriesId);
    }

    // --------------------------------------

    private Season findSeasonBySeriesIdAndSeasonNumber(String seriesId, int seasonNumber) {
        return seasonRepository.findBySeriesIdAndSeasonNumber(seriesId, seasonNumber)
                .orElseThrow(() -> new ResourceNotFoundException(Season.class, "seriesId", seriesId));
    }

    private int getSeasonCountForSeries(String seriesId) {
        return seasonRepository.countBySeriesId(seriesId);
    }

}
