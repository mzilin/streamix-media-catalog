package com.mariuszilinskas.vsp.media.catalog.service;

import com.mariuszilinskas.vsp.media.catalog.dto.SeasonRequest;
import com.mariuszilinskas.vsp.media.catalog.exception.EntityExistsException;
import com.mariuszilinskas.vsp.media.catalog.exception.ResourceNotFoundException;
import com.mariuszilinskas.vsp.media.catalog.model.document.Season;
import com.mariuszilinskas.vsp.media.catalog.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    private final EpisodeAdminService episodeAdminService;

    @Override
    @Transactional
    public Season createSeasonForSeries(String seriesId, SeasonRequest request) {
        logger.info("Creating new Season [number: '{}'] for Series [id: '{}']", request.seasonNumber(), seriesId);
        checkSeasonNumberExists(seriesId, request.seasonNumber());
        return populateNewSeasonWithRequestData(seriesId, request);
    }

    private void checkSeasonNumberExists(String seriesId, int seasonNumber) {
        if (seasonRepository.existsBySeriesIdAndSeasonNumber(seriesId, seasonNumber)) {
            throw new EntityExistsException(Season.class, "seasonNumber", seasonNumber);
        }
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
        checkSeasonNumberExistsExcludeId(seriesId, request.seasonNumber(), id);

        return applySeasonUpdates(season, request);
    }

    private void checkSeasonNumberExistsExcludeId(String seriesId, int seasonNumber, String id) {
        Optional<Season> season = seasonRepository.findBySeriesIdAndSeasonNumber(seriesId, seasonNumber);
        if (season.isPresent() && !season.get().getId().equals(id)) {
            throw new EntityExistsException(Season.class, "seasonNumber", seasonNumber);
        }
    }

    private Season applySeasonUpdates(Season season, SeasonRequest request) {
        season.setSeasonNumber(request.seasonNumber());
        season.setRating(request.rating());
        season.setPoster(request.poster());
        return seasonRepository.save(season);
    }

    @Override
    @Transactional
    public void deleteSeasonFromSeries(String seriesId, String id) {
        logger.info("Deleting Season [id '{}'] from Series [id: '{}']", id, seriesId);
        Season season = findSeasonBySeriesIdAndSeasonId(seriesId, id);
        episodeAdminService.deleteAllEpisodesFromSeason(seriesId, id);
        seasonRepository.delete(season);
    }

    private Season findSeasonBySeriesIdAndSeasonId(String seriesId, String id) {
        return seasonRepository.findByIdAndSeriesId(id, seriesId)
                .orElseThrow(() -> new ResourceNotFoundException(Season.class, "id", id));
    }

    @Override
    @Transactional
    public void deleteAllSeasonsFromSeries(String seriesId) {
        logger.info("Deleting all Seasons from Series [id: '{}']", seriesId);
        episodeAdminService.deleteAllEpisodesFromSeries(seriesId);
        seasonRepository.deleteBySeriesId(seriesId);
    }

}
