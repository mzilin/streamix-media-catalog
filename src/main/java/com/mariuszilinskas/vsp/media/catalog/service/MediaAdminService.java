package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.dto.MovieRequest;
import com.mariuszilinskas.vsp.contentservice.dto.SeriesRequest;
import com.mariuszilinskas.vsp.contentservice.model.document.Movie;
import com.mariuszilinskas.vsp.contentservice.model.document.Series;

public interface MediaAdminService {

    Movie createMovie(MovieRequest request);

    Movie updateMovieById(String mediaId, MovieRequest request);

    void deleteMovieById(String mediaId);

    Series createSeries(SeriesRequest request);

    Series updateSeriesById(String mediaId, SeriesRequest request);

    void deleteSeriesById(String mediaId);

}
