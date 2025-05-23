package com.mariuszilinskas.vsp.contentservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mariuszilinskas.vsp.contentservice.dto.PersonRequest;
import com.mariuszilinskas.vsp.contentservice.model.document.Person;
import com.mariuszilinskas.vsp.contentservice.service.PersonAdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonAdminService personAdminService;

    private static final String BASE_URL = "/admin/people/";
    private static final String personId = "person01";
    private Person person = new Person();
    private PersonRequest personRequest;
    private Person personResponse;

    // ------------------------------------

    @BeforeEach
    void setUp() {
        person.setId(personId);
        person.setName("Leonardo DiCaprio");
        person.setDescription("Academy Award-winning actor known for roles in Titanic, Inception, and The Revenant.");
        person.setBorn(LocalDate.of(1974, 11, 11));
        person.setDied(null);
        person.setImageURL("https://example.com/leonardo_dicaprio.jpg");

        personRequest = new PersonRequest(
                person.getName(),
                person.getDescription(),
                person.getBorn(),
                person.getDied(),
                person.getImageURL()
        );
        personResponse = person;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    private String asJsonString(final Object obj) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.registerModule(new JavaTimeModule()); // Support for LocalDate
//            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//            return objectMapper.writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    // ------------------------------------

    @Test
    void testCreatePerson() throws Exception {
        // Arrange
        Mockito.when(personAdminService.createPerson(Mockito.any(PersonRequest.class))).thenReturn(personResponse);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(personRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(personResponse.getId()))
                .andExpect(jsonPath("$.name").value(personResponse.getName()))
                .andExpect(jsonPath("$.description").value(personResponse.getDescription()))
                .andExpect(jsonPath("$.born").value(personResponse.getBorn()))
                .andExpect(jsonPath("$.died").value(personResponse.getDied()))
                .andExpect(jsonPath("$.imageURL").value(personResponse.getImageURL()));
    }

    @Test
    void testUpdatePerson() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + person.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(personRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.description").value("A famous person"))
                .andExpect(jsonPath("$.born").value("1900-01-01"))
                .andExpect(jsonPath("$.died").value("1980-01-01"))
                .andExpect(jsonPath("$.imageURL").value("http://example.com/image.jpg"));
    }

    @Test
    void testDeletePerson() throws Exception {
        // Arrange
        doNothing().when(personAdminService).deletePerson(personId);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + personId))
                .andExpect(status().isNoContent());

        verify(personAdminService, Mockito.times(1)).deletePerson(personId);
    }

}
