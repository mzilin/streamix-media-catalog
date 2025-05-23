package com.mariuszilinskas.vsp.media.catalog.service;

import com.mariuszilinskas.vsp.media.catalog.dto.MediaRequest;
import com.mariuszilinskas.vsp.media.catalog.dto.MovieRequest;
import com.mariuszilinskas.vsp.media.catalog.dto.SeriesRequest;
import com.mariuszilinskas.vsp.media.catalog.enums.ContentType;
import com.mariuszilinskas.vsp.media.catalog.exception.ResourceNotFoundException;
import com.mariuszilinskas.vsp.media.catalog.model.document.Media;
import com.mariuszilinskas.vsp.media.catalog.model.document.Movie;
import com.mariuszilinskas.vsp.media.catalog.model.document.Series;
import com.mariuszilinskas.vsp.media.catalog.repository.MediaRepository;
import com.mariuszilinskas.vsp.media.catalog.util.CatalogUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing media content, accessible only by system admins.
 * This service handles media creation, updates and deletion.
 *
 * @author Marius Zilinskas
 */
@Service
@RequiredArgsConstructor
public class MediaAdminServiceImpl implements MediaAdminService {

    private static final Logger logger = LoggerFactory.getLogger(MediaAdminServiceImpl.class);
    private final MediaRepository mediaRepository;
    private final SeasonAdminService seasonAdminService;
    private final EpisodeAdminService episodeAdminService;
    private final TrailerAdminService trailerAdminService;

    @Override
    @Transactional
    public Movie createMovie(MovieRequest request) {
        logger.info("Creating new Movie [title: '{}']", request.title());

        Movie newMovie = populateNewMovieWithRequestData(request);

        // TODO: rabbitMQ send request to search service

        return newMovie;
    }

    private Movie populateNewMovieWithRequestData(MovieRequest request) {
        Movie movie = new Movie();
        return applyMovieUpdates(movie, request);
    }

    @Override
    @Transactional
    public Movie updateMovieById(String mediaId, MovieRequest request) {
        logger.info("Updating Movie [id: '{}']", mediaId);

        Movie movie = (Movie) findMediaById(mediaId);
        applyMovieUpdates(movie, request);

        // TODO: rabbitMQ send request to search service

        return movie;
    }

    private Movie applyMovieUpdates(Movie movie, MovieRequest request) {
        applyCommonMediaUpdates(movie, request.mediaAttributes());
        movie.setDirectors(request.directors());
        movie.setWriters(request.writers());
        movie.setDuration(request.duration());
        movie.setContentUrl(request.contentUrl());
        return mediaRepository.save(movie);
    }

    @Override
    @Transactional
    public void deleteMovieById(String mediaId) {
        logger.info("Deleting Movie [id: '{}']", mediaId);
        mediaRepository.deleteById(mediaId);
        trailerAdminService.deleteAllTrailersByMediaId(mediaId);

        // TODO: rabbitMQ send request to search service
    }

    // --------------------------------------

    @Override
    @Transactional
    public Series createSeries(SeriesRequest request) {
        logger.info("Creating new Series [title: '{}']", request.title());

        Series newSeries = populateNewSeriesWithRequestData(request);

        // TODO: rabbitMQ send request to search service

        return newSeries;
    }

    private Series populateNewSeriesWithRequestData(SeriesRequest request) {
        Series series = new Series();
        return applySeriesUpdates(series, request);
    }

    @Override
    @Transactional
    public Series updateSeriesById(String mediaId, SeriesRequest request) {
        logger.info("Updating Series [id: '{}']", mediaId);

        Series series = (Series) findMediaById(mediaId);
        applySeriesUpdates(series, request);

        // TODO: rabbitMQ send request to search service

        return series;
    }

    private Series applySeriesUpdates(Series series, SeriesRequest request) {
        applyCommonMediaUpdates(series, request.commonMediaAttributes());
        series.setCreators(request.creators());
        return mediaRepository.save(series);
    }

    private void applyCommonMediaUpdates(Media media, MediaRequest request) {
        media.setTitle(request.title());
        media.setDescription(request.description());
        media.setType(CatalogUtils.convertStringToEnum(request.type(), ContentType.class));
        media.setReleaseDate(request.releaseDate());
        media.setReleaseCountries(request.releaseCountries());
        media.setRating(request.rating());
        media.setGenres(request.genres());
        media.setCast(request.cast());
        media.setCrew(request.crew());
        media.setAvailableCountries(request.availableCountries());
        media.setTags(request.tags());
        media.setPoster(request.poster());
    }

    @Override
    @Transactional
    public void deleteSeriesById(String mediaId) {
        logger.info("Deleting Series [id: '{}']", mediaId);
        episodeAdminService.deleteAllEpisodesFromSeries(mediaId);
        seasonAdminService.deleteAllSeasonsFromSeries(mediaId);
        trailerAdminService.deleteAllTrailersByMediaId(mediaId);
        mediaRepository.deleteById(mediaId);

        // TODO: rabbitMQ send request to search service
    }

    // --------------------------------------

    private Media findMediaById(String id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Media.class, "id", id));
    }

}
