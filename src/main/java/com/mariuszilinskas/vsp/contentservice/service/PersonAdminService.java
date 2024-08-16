package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.dto.PersonRequest;
import com.mariuszilinskas.vsp.contentservice.model.document.Person;

public interface PersonAdminService {

    Person createPerson(PersonRequest request);

    Person updatePerson(String id, PersonRequest request);

    void deletePerson(String id);

}
