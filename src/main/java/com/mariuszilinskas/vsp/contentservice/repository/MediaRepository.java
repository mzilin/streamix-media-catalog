package com.mariuszilinskas.vsp.contentservice.repository;

import com.mariuszilinskas.vsp.contentservice.model.document.Media;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends MongoRepository<Media, String> {
}
