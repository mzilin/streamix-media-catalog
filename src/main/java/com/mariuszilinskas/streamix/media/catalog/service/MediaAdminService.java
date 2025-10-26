package com.mariuszilinskas.streamix.media.catalog.service;

import com.mariuszilinskas.streamix.media.catalog.dto.MovieRequest;
import com.mariuszilinskas.streamix.media.catalog.dto.SeriesRequest;
import com.mariuszilinskas.streamix.media.catalog.model.document.Movie;
import com.mariuszilinskas.streamix.media.catalog.model.document.Series;

public interface MediaAdminService {

    Movie createMovie(MovieRequest request);

    Movie updateMovieById(String mediaId, MovieRequest request);

    void deleteMovieById(String mediaId);

    Series createSeries(SeriesRequest request);

    Series updateSeriesById(String mediaId, SeriesRequest request);

    void deleteSeriesById(String mediaId);

}
