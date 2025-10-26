package com.mariuszilinskas.streamix.media.catalog.repository;

import com.mariuszilinskas.streamix.media.catalog.model.document.Media;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends MongoRepository<Media, String> {
}
