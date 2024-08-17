package com.mariuszilinskas.vsp.contentservice.repository;

import com.mariuszilinskas.vsp.contentservice.model.document.Trailer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrailerRepository extends MongoRepository<Trailer, String> {

    Optional<Trailer> findByIdAndMediaId(String id, String mediaId);

}
