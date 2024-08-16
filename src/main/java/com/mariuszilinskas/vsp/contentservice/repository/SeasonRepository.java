package com.mariuszilinskas.vsp.contentservice.repository;

import com.mariuszilinskas.vsp.contentservice.model.document.Season;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeasonRepository extends MongoRepository<Season, String> {

    Optional<Season> findByIdAndSeriesId(String id, String seriesId);

    int countBySeriesId(String seriesId);

    void deleteBySeriesId(String seriesId);

}
