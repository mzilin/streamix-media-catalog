package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.dto.TrailerRequest;
import com.mariuszilinskas.vsp.contentservice.exception.ResourceNotFoundException;
import com.mariuszilinskas.vsp.contentservice.model.document.Trailer;
import com.mariuszilinskas.vsp.contentservice.repository.TrailerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrailerAdminServiceImplTest {

    @Mock
    private TrailerRepository trailerRepository;

    @InjectMocks
    private TrailerAdminServiceImpl trailerAdminService;

    private final String trailerId = "trailer01";
    private final String mediaId = "media01";
    private final Trailer trailer = new Trailer();
    private final Trailer trailer2 = new Trailer();

    private TrailerRequest createRequest;
    private TrailerRequest updateRequest;

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

        createRequest = new TrailerRequest(
                trailer.getTitle(),
                trailer.getDescription(),
                trailer.getDuration(),
                trailer.getThumbnail(),
                trailer.getContentUrl()
        );

        trailer2.setId("trailer02");
        trailer2.setMediaId(mediaId);
        trailer2.setTitle("Fight Club Teaser");
        trailer2.setDescription("");
        trailer2.setDuration(150);
        trailer2.setThumbnail("image_url");
        trailer2.setContentUrl("content_url");

        updateRequest = new TrailerRequest(
                trailer2.getTitle(),
                trailer2.getDescription(),
                trailer2.getDuration(),
                trailer2.getThumbnail(),
                trailer2.getContentUrl()
        );
    }

    // ------------------------------------

    @Test
    void testCreateTrailer() {
        // Arrange
        ArgumentCaptor<Trailer> captor = ArgumentCaptor.forClass(Trailer.class);

        when(trailerRepository.save(captor.capture())).thenReturn(trailer);

        // Act
        Trailer response = trailerAdminService.createTrailer(mediaId, createRequest);

        // Assert
        assertNotNull(response);
        assertEquals(mediaId, response.getMediaId());
        assertEquals(createRequest.title(), response.getTitle());

        verify(trailerRepository, times(1)).save(captor.capture());

        Trailer savedTrailer = captor.getValue();
        assertEquals(trailer.getMediaId(), savedTrailer.getMediaId());
        assertEquals(trailer.getTitle(), savedTrailer.getTitle());
        assertEquals(trailer.getDescription(), savedTrailer.getDescription());
        assertEquals(trailer.getDuration(), savedTrailer.getDuration());
        assertEquals(trailer.getThumbnail(), savedTrailer.getThumbnail());
        assertEquals(trailer.getContentUrl(), savedTrailer.getContentUrl());
    }

    // ------------------------------------

    @Test
    void testUpdateTrailer() {
        // Arrange
        ArgumentCaptor<Trailer> captor = ArgumentCaptor.forClass(Trailer.class);

        when(trailerRepository.findByIdAndMediaId(trailerId, mediaId)).thenReturn(Optional.of(trailer));
        when(trailerRepository.save(captor.capture())).thenReturn(trailer2);

        // Act
        Trailer response = trailerAdminService.updateTrailer(trailerId, mediaId, updateRequest);

        // Assert
        assertNotNull(response);
        assertEquals(updateRequest.title(), response.getTitle());

        verify(trailerRepository, times(1)).findByIdAndMediaId(trailerId, mediaId);
        verify(trailerRepository, times(1)).save(captor.capture());

        Trailer savedTrailer = captor.getValue();
        assertEquals(trailer2.getMediaId(), savedTrailer.getMediaId());
        assertEquals(trailer2.getTitle(), savedTrailer.getTitle());
        assertEquals(trailer2.getDescription(), savedTrailer.getDescription());
        assertEquals(trailer2.getDuration(), savedTrailer.getDuration());
        assertEquals(trailer2.getThumbnail(), savedTrailer.getThumbnail());
        assertEquals(trailer2.getContentUrl(), savedTrailer.getContentUrl());
    }

    @Test
    void testUpdateTrailer_TrailerDoesntExist() {
        // Arrange
        when(trailerRepository.findByIdAndMediaId(trailerId, mediaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            trailerAdminService.updateTrailer(trailerId, mediaId, updateRequest);
        });

        // Assert
        verify(trailerRepository, times(1)).findByIdAndMediaId(trailerId, mediaId);

        verify(trailerRepository, never()).save(any(Trailer.class));
    }

    // ------------------------------------

    @Test
    void testDeleteTrailer() {
        // Arrange
        when(trailerRepository.findByIdAndMediaId(trailerId, mediaId)).thenReturn(Optional.of(trailer));
        doNothing().when(trailerRepository).delete(trailer);

        // Act
        trailerAdminService.deleteTrailer(trailerId, mediaId);

        // Assert
        verify(trailerRepository, times(1)).findByIdAndMediaId(trailerId, mediaId);
        verify(trailerRepository, times(1)).delete(trailer);
    }

    @Test
    void testDeleteTrailer_TrailerDoesntExist() {
        // Arrange
        when(trailerRepository.findByIdAndMediaId(trailerId, mediaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            trailerAdminService.deleteTrailer(trailerId, mediaId);
        });

        // Assert
        verify(trailerRepository, times(1)).findByIdAndMediaId(trailerId, mediaId);

        verify(trailerRepository, never()).delete(any(Trailer.class));
    }

}
