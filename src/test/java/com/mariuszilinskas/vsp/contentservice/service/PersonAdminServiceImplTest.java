package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.dto.PersonRequest;
import com.mariuszilinskas.vsp.contentservice.model.document.Person;
import com.mariuszilinskas.vsp.contentservice.repository.PersonRepository;
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

    // ------------------------------------

    @BeforeEach
    void setUp() {
        person.setId(personId);
        person.setName("Leonardo DiCaprio");
        person.setDescription("Academy Award-winning actor known for roles in Titanic, Inception, and The Revenant.");
        person.setBorn(LocalDate.of(1974, 11, 11));
        person.setDied(null);
        person.setImageURL("https://example.com/leonardo_dicaprio.jpg");
    }

    // ------------------------------------

    @Test
    void testCreatePerson() {
        // Arrange
        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);

        PersonRequest request = new PersonRequest(
                person.getName(),
                person.getDescription(),
                person.getBorn(),
                person.getDied(),
                person.getImageURL()
        );

        when(personRepository.save(captor.capture())).thenReturn(person);

        // Act
        Person response = personAdminService.createPerson(request);

        // Assert
        assertNotNull(response);
        assertEquals(request.name(), response.getName());

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

        // Act

        // Assert
    }

    @Test
    void testUpdatePerson_PersonDoesntExist() {
        // Arrange

        // Act

        // Assert
    }

    // ------------------------------------

    @Test
    void testDeletePerson() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void testDeletePerson_PersonDoesntExist() {
        // Arrange

        // Act

        // Assert
    }

}
