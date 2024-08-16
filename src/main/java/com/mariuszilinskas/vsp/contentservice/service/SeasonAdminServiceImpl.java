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

        Season newSeason = populateNewSeasonWithRequestData(seriesId, request);
        int currentSeasonCount = getSeasonCountForSeries(seriesId);
        mediaAdminService.updateSeriesSeasonCount(seriesId, currentSeasonCount + 1);

        return newSeason;
    }

    private Season populateNewSeasonWithRequestData(String seriesId, SeasonRequest request) {
        Season season = new Season();
        season.setSeriesId(seriesId);
        return applySeasonUpdates(season, request);
    }

    @Override
    @Transactional
    public Season updateSeasonInSeries(String seriesId, String id, SeasonRequest request) {
        logger.info("Updating new Season [id: '{}'] for Series [id: '{}']", id, seriesId);

        Season season = findSeasonBySeriesIdAndSeasonId(seriesId, id);
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
    public Season updateSeasonEpisodeCount(String seriesId, String id, int episodeCount) {
        logger.info("Updating Series [id: '{}'] Season [id '{}'] episode count to: '{}'", seriesId, id, episodeCount);

        Season season = findSeasonBySeriesIdAndSeasonId(seriesId, id);
        season.setEpisodeCount(episodeCount);

        return seasonRepository.save(season);
    }

    @Override
    @Transactional
    public void removeSeasonFromSeries(String seriesId, String id) {
        logger.info("Removing Season [id '{}'] from Series [id: '{}']", id, seriesId);

        int currentSeasonCount = getSeasonCountForSeries(seriesId);
        mediaAdminService.updateSeriesSeasonCount(seriesId, currentSeasonCount - 1);

        episodeAdminService.removeAllEpisodesFromSeason(seriesId, id);

    }

    @Override
    @Transactional
    public void removeAllSeasonsFromSeries(String seriesId) {
        logger.info("Removing all Seasons from Series [id: '{}']", seriesId);

        seasonRepository.deleteBySeriesId(seriesId);
        episodeAdminService.removeAllEpisodesFromSeries(seriesId);
    }

    // --------------------------------------

    private Season findSeasonBySeriesIdAndSeasonId(String seriesId, String id) {
        return seasonRepository.findByIdAndSeriesId(id, seriesId)
                .orElseThrow(() -> new ResourceNotFoundException(Season.class, "id", id));
    }

    private int getSeasonCountForSeries(String seriesId) {
        return seasonRepository.countBySeriesId(seriesId);
    }

}
