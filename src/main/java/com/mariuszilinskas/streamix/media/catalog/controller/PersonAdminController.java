package com.mariuszilinskas.streamix.media.catalog.controller;

import com.mariuszilinskas.streamix.media.catalog.dto.PersonRequest;
import com.mariuszilinskas.streamix.media.catalog.model.document.Person;
import com.mariuszilinskas.streamix.media.catalog.service.PersonAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class provides REST APIs for handling CRUD operations related to people content
 * that are only accessible by system admins.
 *
 * @author Marius Zilinskas
 */
@RestController
@RequestMapping("/admin/people")
@RequiredArgsConstructor
public class PersonAdminController {

    private final PersonAdminService personAdminService;

    @PostMapping
    public ResponseEntity<Person> createPerson(@Valid @RequestBody PersonRequest request){
        Person response = personAdminService.createPerson(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{personId}")
    public ResponseEntity<Person> updatePerson(
            @PathVariable String personId,
            @Valid @RequestBody PersonRequest request
    ){
        Person response = personAdminService.updatePerson(personId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<Void> deletePerson(@PathVariable String personId){
        personAdminService.deletePerson(personId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
