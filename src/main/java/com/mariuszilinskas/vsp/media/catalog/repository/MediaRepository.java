package com.mariuszilinskas.vsp.media.catalog.repository;

import com.mariuszilinskas.vsp.media.catalog.model.document.Media;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends MongoRepository<Media, String> {
}
