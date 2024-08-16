package com.mariuszilinskas.vsp.contentservice.repository;

import com.mariuszilinskas.vsp.contentservice.model.document.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends MongoRepository<Person, String> {
}
