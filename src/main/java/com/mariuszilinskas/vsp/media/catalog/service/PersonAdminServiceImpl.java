package com.mariuszilinskas.vsp.media.catalog.service;

import com.mariuszilinskas.vsp.media.catalog.dto.PersonRequest;
import com.mariuszilinskas.vsp.media.catalog.exception.ResourceNotFoundException;
import com.mariuszilinskas.vsp.media.catalog.model.document.Person;
import com.mariuszilinskas.vsp.media.catalog.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing people content, accessible only by system admins.
 * This service handles person creation, updates and deletion.
 *
 * @author Marius Zilinskas
 */
@Service
@RequiredArgsConstructor
public class PersonAdminServiceImpl implements PersonAdminService {

    private static final Logger logger = LoggerFactory.getLogger(PersonAdminServiceImpl.class);
    private final PersonRepository personRepository;

    @Override
    @Transactional
    public Person createPerson(PersonRequest request) {
        logger.info("Creating new Person [name: '{}']", request.name());
        return populateNewPersonWithRequestData(request);
    }

    private Person populateNewPersonWithRequestData(PersonRequest request) {
        Person person = new Person();
        return applyPersonUpdates(person, request);
    }

    @Override
    @Transactional
    public Person updatePerson(String id, PersonRequest request) {
        logger.info("Updating Person [id: '{}']", id);
        Person person = findPersonById(id);
        return applyPersonUpdates(person, request);
    }

    private Person applyPersonUpdates(Person person, PersonRequest request) {
        person.setName(request.name());
        person.setDescription(request.description());
        person.setBorn(request.born());
        person.setDied(request.died());
        person.setImageURL(request.imageURL());
        return personRepository.save(person);
    }

    @Override
    @Transactional
    public void deletePerson(String id) {
        logger.info("Deleting Person [id: '{}']", id);
        Person person = findPersonById(id);
        personRepository.delete(person);
    }

    private Person findPersonById(String id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Person.class, "id", id));
    }

}
