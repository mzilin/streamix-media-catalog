package com.mariuszilinskas.vsp.media.catalog.service;

import com.mariuszilinskas.vsp.media.catalog.dto.MovieRequest;
import com.mariuszilinskas.vsp.media.catalog.dto.SeriesRequest;
import com.mariuszilinskas.vsp.media.catalog.model.document.Movie;
import com.mariuszilinskas.vsp.media.catalog.model.document.Series;

public interface MediaAdminService {

    Movie createMovie(MovieRequest request);

    Movie updateMovieById(String mediaId, MovieRequest request);

    void deleteMovieById(String mediaId);

    Series createSeries(SeriesRequest request);

    Series updateSeriesById(String mediaId, SeriesRequest request);

    void deleteSeriesById(String mediaId);

}
