package com.mariuszilinskas.vsp.media.catalog.service;

import com.mariuszilinskas.vsp.media.catalog.dto.PersonRequest;
import com.mariuszilinskas.vsp.media.catalog.model.document.Person;

public interface PersonAdminService {

    Person createPerson(PersonRequest request);

    Person updatePerson(String id, PersonRequest request);

    void deletePerson(String id);

}
