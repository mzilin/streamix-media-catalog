package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.dto.MediaRequest;
import com.mariuszilinskas.vsp.contentservice.dto.MovieRequest;
import com.mariuszilinskas.vsp.contentservice.dto.SeriesRequest;
import com.mariuszilinskas.vsp.contentservice.enums.ContentType;
import com.mariuszilinskas.vsp.contentservice.exception.ResourceNotFoundException;
import com.mariuszilinskas.vsp.contentservice.model.document.Media;
import com.mariuszilinskas.vsp.contentservice.model.document.Movie;
import com.mariuszilinskas.vsp.contentservice.model.document.Series;
import com.mariuszilinskas.vsp.contentservice.repository.MediaRepository;
import com.mariuszilinskas.vsp.contentservice.util.ContentUtils;
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
    public void removeMovieById(String mediaId) {
        logger.info("Removing Movie [id: '{}']", mediaId);
        mediaRepository.deleteById(mediaId);

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

    @Override
    @Transactional
    public Series updateSeriesSeasonCount(String mediaId, int seasonCount) {
        logger.info("Updating Series [id: '{}'] season count to: '{}'", mediaId, seasonCount);

        Series series = (Series) findMediaById(mediaId);
        series.setSeasonCount(seasonCount);

        return mediaRepository.save(series);
    }

    private Series applySeriesUpdates(Series series, SeriesRequest request) {
        applyCommonMediaUpdates(series, request.commonMediaAttributes());
        series.setCreators(request.creators());
        return mediaRepository.save(series);
    }

    private void applyCommonMediaUpdates(Media media, MediaRequest request) {
        media.setTitle(request.title());
        media.setDescription(request.description());
        media.setType(ContentUtils.convertStringToEnum(request.type(), ContentType.class));
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
    public void removeSeriesById(String mediaId) {
        logger.info("Removing Series [id: '{}']", mediaId);
        mediaRepository.deleteById(mediaId);
        seasonAdminService.removeAllSeasonsFromSeries(mediaId);

        // TODO: rabbitMQ send request to search service
    }

    // --------------------------------------

    private Media findMediaById(String id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Media.class, "id", id));
    }

}
