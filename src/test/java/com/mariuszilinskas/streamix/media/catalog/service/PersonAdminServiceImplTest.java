package com.mariuszilinskas.streamix.media.catalog.service;

import com.mariuszilinskas.streamix.media.catalog.dto.PersonRequest;
import com.mariuszilinskas.streamix.media.catalog.exception.ResourceNotFoundException;
import com.mariuszilinskas.streamix.media.catalog.model.document.Person;
import com.mariuszilinskas.streamix.media.catalog.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonAdminServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonAdminServiceImpl personAdminService;

    private final String personId = "person01";
    private final Person person = new Person();
    private final Person person2 = new Person();

    private PersonRequest createRequest;
    private PersonRequest updateRequest;

    // ------------------------------------

    @BeforeEach
    void setUp() {
        person.setId(personId);
        person.setName("Leonardo DiCaprio");
        person.setDescription("Academy Award-winning actor known for roles in Titanic, Inception, and The Revenant.");
        person.setBorn(LocalDate.of(1974, 11, 11));
        person.setDied(null);
        person.setImageURL("https://example.com/leonardo_dicaprio.jpg");

        createRequest = new PersonRequest(
                person.getName(),
                person.getDescription(),
                person.getBorn(),
                person.getDied(),
                person.getImageURL()
        );

        person2.setId("person02");
        person2.setName("Al Pacino");
        person2.setDescription("American actor and filmmaker");
        person2.setBorn(LocalDate.of(1940, 4, 25));
        person2.setDied(null);
        person2.setImageURL("https://example.com/alpacino.jpg");

        updateRequest = new PersonRequest(
                person2.getName(),
                person2.getDescription(),
                person2.getBorn(),
                person2.getDied(),
                person2.getImageURL()
        );
    }

    // ------------------------------------

    @Test
    void testCreatePerson() {
        // Arrange
        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);

        when(personRepository.save(captor.capture())).thenReturn(person);

        // Act
        Person response = personAdminService.createPerson(createRequest);

        // Assert
        assertNotNull(response);
        assertEquals(createRequest.name(), response.getName());

        verify(personRepository, times(1)).save(captor.capture());

        Person savedPerson = captor.getValue();
        assertEquals(person.getName(), savedPerson.getName());
        assertEquals(person.getDescription(), savedPerson.getDescription());
        assertEquals(person.getBorn(), savedPerson.getBorn());
        assertEquals(person.getDied(), savedPerson.getDied());
        assertEquals(person.getImageURL(), savedPerson.getImageURL());
    }

    // ------------------------------------

    @Test
    void testUpdatePerson() {
        // Arrange
        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(personRepository.save(captor.capture())).thenReturn(person2);

        // Act
        Person response = personAdminService.updatePerson(personId, updateRequest);

        // Assert
        assertNotNull(response);
        assertEquals(updateRequest.name(), response.getName());

        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).save(captor.capture());

        Person savedPerson = captor.getValue();
        assertEquals(person2.getName(), savedPerson.getName());
        assertEquals(person2.getDescription(), savedPerson.getDescription());
        assertEquals(person2.getBorn(), savedPerson.getBorn());
        assertEquals(person2.getDied(), savedPerson.getDied());
        assertEquals(person2.getImageURL(), savedPerson.getImageURL());
    }

    @Test
    void testUpdatePerson_PersonDoesntExist() {
        // Arrange
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            personAdminService.updatePerson(personId, updateRequest);
        });

        // Assert
        verify(personRepository, times(1)).findById(personId);

        verify(personRepository, never()).save(any(Person.class));
    }

    // ------------------------------------

    @Test
    void testDeletePerson() {
        // Arrange
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        doNothing().when(personRepository).delete(person);

        // Act
        personAdminService.deletePerson(personId);

        // Assert
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).delete(person);
    }

    @Test
    void testDeletePerson_PersonDoesntExist() {
        // Arrange
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            personAdminService.deletePerson(personId);
        });

        // Assert
        verify(personRepository, times(1)).findById(personId);

        verify(personRepository, never()).delete(any(Person.class));
    }

}
