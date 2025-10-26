package com.mariuszilinskas.streamix.media.catalog.repository;

import com.mariuszilinskas.streamix.media.catalog.model.document.Season;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeasonRepository extends MongoRepository<Season, String> {

    Optional<Season> findByIdAndSeriesId(String id, String seriesId);

    Optional<Season> findBySeriesIdAndSeasonNumber(String seriesId, int seasonNumber);

    void deleteBySeriesId(String seriesId);

    boolean existsBySeriesIdAndSeasonNumber(String seriesId, int seasonNumber);

}
