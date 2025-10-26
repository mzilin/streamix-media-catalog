package com.mariuszilinskas.streamix.media.catalog.service;

import com.mariuszilinskas.streamix.media.catalog.dto.PersonRequest;
import com.mariuszilinskas.streamix.media.catalog.model.document.Person;

public interface PersonAdminService {

    Person createPerson(PersonRequest request);

    Person updatePerson(String id, PersonRequest request);

    void deletePerson(String id);

}
