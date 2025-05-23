package com.mariuszilinskas.vsp.media.catalog.repository;

import com.mariuszilinskas.vsp.media.catalog.model.document.Trailer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrailerRepository extends MongoRepository<Trailer, String> {

    Optional<Trailer> findByIdAndMediaId(String id, String mediaId);

    void deleteByMediaId(String mediaId);

}
