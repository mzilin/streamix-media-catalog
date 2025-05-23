package com.mariuszilinskas.vsp.contentservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariuszilinskas.vsp.contentservice.dto.TrailerRequest;
import com.mariuszilinskas.vsp.contentservice.model.document.Trailer;
import com.mariuszilinskas.vsp.contentservice.service.TrailerAdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TrailerAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrailerAdminService trailerAdminService;

    private static final String trailerId = "trailer01";
    private static final String mediaId = "media01";
    private final Trailer trailer = new Trailer();
    private TrailerRequest trailerRequest;
    private Trailer trailerResponse;

    private static final String BASE_URL = "/admin/media/" + mediaId + "/trailers";

    // ------------------------------------

    @BeforeEach
    void setUp() {
        trailer.setId(trailerId);
        trailer.setMediaId(mediaId);
        trailer.setTitle("Fight Club Trailer");
        trailer.setDescription("");
        trailer.setDuration(250);
        trailer.setThumbnail("image_url");
        trailer.setContentUrl("content_url");

        trailerRequest = new TrailerRequest(
                trailer.getTitle(),
                trailer.getDescription(),
                trailer.getDuration(),
                trailer.getThumbnail(),
                trailer.getContentUrl()
        );
        trailerResponse = trailer;
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ------------------------------------

    @Test
    void testCreateTrailer() throws Exception {
        // Arrange
        when(trailerAdminService.createTrailer(mediaId, trailerRequest)).thenReturn(trailerResponse);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(trailerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(trailer.getId()))
                .andExpect(jsonPath("$.mediaId").value(trailer.getMediaId()))
                .andExpect(jsonPath("$.title").value(trailer.getTitle()))
                .andExpect(jsonPath("$.description").value(trailer.getDescription()))
                .andExpect(jsonPath("$.duration").value(trailer.getDuration()))
                .andExpect(jsonPath("$.thumbnail").value(trailer.getThumbnail()))
                .andExpect(jsonPath("$.contentUrl").value(trailer.getContentUrl()));

        verify(trailerAdminService, times(1)).createTrailer(mediaId, trailerRequest);
    }

//    @Test
//    void testCreateTrailerE2E() throws Exception {
//        // Arrange: Seed test database with any preconditions (optional).
//
//        // Act: Send a POST request to the endpoint.
//        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + mediaId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(trailerRequest)))
//                // Assert: Validate the response status and content.
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").isNotEmpty());
////                .andExpect(jsonPath("$.mediaId").value(mediaId))
////                .andExpect(jsonPath("$.title").value(trailer.getTitle()))
////                .andExpect(jsonPath("$.description").value(trailer.getDescription()))
////                .andExpect(jsonPath("$.duration").value(trailer.getDuration()))
////                .andExpect(jsonPath("$.thumbnail").value(trailer.getThumbnail()))
////                .andExpect(jsonPath("$.contentUrl").value(trailer.getContentUrl()));
//
//        // Optionally: Verify database changes if using an in-memory or real test database.
//        // e.g., assert that the trailer was saved.
//    }


    // ------------------------------------

    @Test
    void testUpdateTrailer() throws Exception {
        // Arrange
        when(trailerAdminService.updateTrailer(trailerId, mediaId, trailerRequest)).thenReturn(trailerResponse);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + trailerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(trailerResponse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(trailer.getId()))
                .andExpect(jsonPath("$.mediaId").value(trailer.getMediaId()))
                .andExpect(jsonPath("$.title").value(trailer.getTitle()))
                .andExpect(jsonPath("$.description").value(trailer.getDescription()))
                .andExpect(jsonPath("$.duration").value(trailer.getDuration()))
                .andExpect(jsonPath("$.thumbnail").value(trailer.getThumbnail()))
                .andExpect(jsonPath("$.contentUrl").value(trailer.getContentUrl()));

        verify(trailerAdminService, times(1)).updateTrailer(trailerId, mediaId, trailerRequest);
    }

    // ------------------------------------

    @Test
    void testDeleteTrailer() throws Exception {
        // Arrange
        doNothing().when(trailerAdminService).deleteTrailer(trailerId, mediaId);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + trailerId))
                .andExpect(status().isNoContent());

        verify(trailerAdminService, times(1)).deleteTrailer(trailerId, mediaId);
    }

}
