package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.dto.EpisodeRequest;
import com.mariuszilinskas.vsp.contentservice.exception.EntityExistsException;
import com.mariuszilinskas.vsp.contentservice.exception.ResourceNotFoundException;
import com.mariuszilinskas.vsp.contentservice.model.document.Episode;
import com.mariuszilinskas.vsp.contentservice.repository.EpisodeRepository;
import java.time.LocalDate;
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
public class EpisodeAdminServiceImplTest {

    @Mock
    private EpisodeRepository episodeRepository;

    @Mock
    private SeasonAdminService seasonAdminService;

    @InjectMocks
    private EpisodeAdminServiceImpl episodeAdminService;

    private final String seriesId = "series01";
    private final String seasonId = "season01";
    private final String episodeId = "episode01";
    private final Episode episode = new Episode();
    private EpisodeRequest episodeRequest;
    private EpisodeRequest updatedEpisodeRequest;

    // ------------------------------------

    @BeforeEach
    void setUp() {
        episode.setId(episodeId);
        episode.setSeriesId(seriesId);
        episode.setSeasonId(seasonId);
        episode.setEpisodeNumber(1);
        episode.setTitle("Pilot");
        episode.setDescription("Pilot episode");
        episode.setReleaseDate(LocalDate.of(2024, 5, 21));
        episode.setDuration(120);
        episode.setThumbnail("https://example.com/image.jpg");
        episode.setContentUrl("https://example.com/content.mp4");

        episodeRequest = new EpisodeRequest(
                episode.getEpisodeNumber(),
                episode.getTitle(),
                episode.getDescription(),
                episode.getReleaseDate(),
                episode.getRating(),
                episode.getDuration(),
                episode.getThumbnail(),
                episode.getContentUrl()
        );

        updatedEpisodeRequest = new EpisodeRequest(
                episode.getEpisodeNumber(),
                "Updated Title",
                "Updated description",
                episodeRequest.releaseDate(),
                episodeRequest.rating(),
                episodeRequest.duration(),
                episodeRequest.thumbnail(),
                episodeRequest.contentUrl()
        );
    }

    // ------------------------------------

    @Test
    void testCreateEpisodeInSeason_Success() {
        // Arrange
        ArgumentCaptor<Episode> captor = ArgumentCaptor.forClass(Episode.class);
        int episodeNumber = episodeRequest.episodeNumber();

        when(episodeRepository.existsBySeriesIdAndSeasonIdAndEpisodeNumber(seriesId, seasonId, episodeNumber))
                .thenReturn(false);
        when(episodeRepository.save(captor.capture())).thenReturn(episode);

        // Act
        Episode response = episodeAdminService.createEpisodeInSeason(seriesId, seasonId, episodeRequest);

        // Assert
        assertNotNull(response);
        assertEquals(episode.getId(), response.getId());

        verify(episodeRepository, times(1)).existsBySeriesIdAndSeasonIdAndEpisodeNumber(seriesId, seasonId, episodeNumber);
        verify(episodeRepository, times(1)).save(captor.capture());

        Episode savedEpisode = captor.getValue();
        assertEquals(episodeRequest.title(), savedEpisode.getTitle());
        assertEquals(episodeRequest.description(), savedEpisode.getDescription());
        assertEquals(episodeRequest.releaseDate(), savedEpisode.getReleaseDate());
        assertEquals(episodeRequest.rating(), savedEpisode.getRating());
        assertEquals(episodeRequest.duration(), savedEpisode.getDuration());
        assertEquals(episodeRequest.thumbnail(), savedEpisode.getThumbnail());
        assertEquals(episodeRequest.contentUrl(), savedEpisode.getContentUrl());
    }

    @Test
    void testCreateEpisodeInSeason_EpisodeAlreadyExists() {
        // Arrange
        int episodeNumber = episodeRequest.episodeNumber();
        when(episodeRepository.existsBySeriesIdAndSeasonIdAndEpisodeNumber(seriesId, seasonId, episodeNumber))
                .thenReturn(true);

        // Act & Assert
        assertThrows(EntityExistsException.class, () -> {
            episodeAdminService.createEpisodeInSeason(seriesId, seasonId, episodeRequest);
        });

        // Assert
        verify(episodeRepository, times(1)).existsBySeriesIdAndSeasonIdAndEpisodeNumber(seriesId, seasonId, episodeNumber);
        verify(episodeRepository, never()).save(any(Episode.class));
    }

    // ------------------------------------

    @Test
    void testUpdateEpisodeInSeason() {
        ArgumentCaptor<Episode> captor = ArgumentCaptor.forClass(Episode.class);
        int episodeNumber = episodeRequest.episodeNumber();

        when(episodeRepository.findByIdAndSeriesIdAndSeasonId(episodeId, seriesId, seasonId))
                .thenReturn(Optional.of(episode));
        when(episodeRepository.findBySeriesIdAndSeasonIdAndEpisodeNumber(seriesId, seasonId, episodeNumber))
                .thenReturn(Optional.of(episode));
        when(episodeRepository.save(captor.capture())).thenReturn(episode);

        // Act
        Episode response = episodeAdminService.updateEpisodeInSeason(seriesId, seasonId, episodeId, updatedEpisodeRequest);

        // Assert
        assertNotNull(response);
        verify(episodeRepository, times(1)).findByIdAndSeriesIdAndSeasonId(episodeId, seriesId, seasonId);
        verify(episodeRepository, times(1)).findBySeriesIdAndSeasonIdAndEpisodeNumber(seriesId, seasonId, episodeNumber);
        verify(episodeRepository, times(1)).save(captor.capture());

        Episode savedEpisode = captor.getValue();
        assertEquals(episodeId, savedEpisode.getId());
        assertEquals(seriesId, savedEpisode.getSeriesId());
        assertEquals(seasonId, savedEpisode.getSeasonId());
        assertEquals(updatedEpisodeRequest.episodeNumber(), savedEpisode.getEpisodeNumber());
        assertEquals(updatedEpisodeRequest.title(), savedEpisode.getTitle());
        assertEquals(updatedEpisodeRequest.description(), savedEpisode.getDescription());
        assertEquals(updatedEpisodeRequest.releaseDate(), savedEpisode.getReleaseDate());
        assertEquals(updatedEpisodeRequest.rating(), savedEpisode.getRating());
        assertEquals(updatedEpisodeRequest.duration(), savedEpisode.getDuration());
        assertEquals(updatedEpisodeRequest.thumbnail(), savedEpisode.getThumbnail());
        assertEquals(updatedEpisodeRequest.contentUrl(), savedEpisode.getContentUrl());
    }

    @Test
    void testUpdateEpisodeInSeason_DuplicateEpisodeNumber() {
        int episodeNumber = updatedEpisodeRequest.episodeNumber();

        Episode duplicateEpisode = new Episode();
        duplicateEpisode.setId("some-id");
        duplicateEpisode.setSeriesId(seriesId);
        duplicateEpisode.setSeasonId(seasonId);
        duplicateEpisode.setEpisodeNumber(episodeNumber);

        when(episodeRepository.findByIdAndSeriesIdAndSeasonId(episodeId, seriesId, seasonId))
                .thenReturn(Optional.of(episode));
        when(episodeRepository.findBySeriesIdAndSeasonIdAndEpisodeNumber(seriesId, seasonId, episodeNumber))
                .thenReturn(Optional.of(duplicateEpisode));

        // Act & Assert
        assertThrows(EntityExistsException.class, () -> {
            episodeAdminService.updateEpisodeInSeason(seriesId, seasonId, episodeId, updatedEpisodeRequest);
        });

        verify(episodeRepository, times(1)).findByIdAndSeriesIdAndSeasonId(episodeId, seriesId, seasonId);
        verify(episodeRepository, times(1)).findBySeriesIdAndSeasonIdAndEpisodeNumber(seriesId, seasonId, episodeNumber);

        verify(episodeRepository, never()).save(any(Episode.class));
    }

    @Test
    void testUpdateEpisodeInSeason_EpisodeDoesntExist() {
        // Assert
        when(episodeRepository.findByIdAndSeriesIdAndSeasonId(episodeId, seriesId, seasonId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            episodeAdminService.updateEpisodeInSeason(seriesId, seasonId, episodeId, updatedEpisodeRequest);
        });

        verify(episodeRepository, times(1)).findByIdAndSeriesIdAndSeasonId(episodeId, seriesId, seasonId);

        verify(episodeRepository, never()).findBySeriesIdAndSeasonIdAndEpisodeNumber(anyString(), anyString(), anyInt());
        verify(episodeRepository, never()).save(any(Episode.class));
    }

    // ------------------------------------

    @Test
    void testDeleteEpisodeFromSeason() {
        // Arrange
        when(episodeRepository.findByIdAndSeriesIdAndSeasonId(episodeId, seriesId, seasonId))
                .thenReturn(Optional.of(episode));

        // Act
        episodeAdminService.deleteEpisodeFromSeason(seriesId, seasonId, episodeId);

        // Assert
        verify(episodeRepository, times(1)).findByIdAndSeriesIdAndSeasonId(episodeId, seriesId, seasonId);
        verify(episodeRepository, times(1)).delete(episode);
    }

    @Test
    void testDeleteEpisodeFromSeason_EpisodeDoesntExist() {
        // Arrange
        when(episodeRepository.findByIdAndSeriesIdAndSeasonId(episodeId, seriesId, seasonId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            episodeAdminService.deleteEpisodeFromSeason(seriesId, seasonId, episodeId);
        });

        // Assert
        verify(episodeRepository, times(1)).findByIdAndSeriesIdAndSeasonId(episodeId, seriesId, seasonId);

        verify(episodeRepository, never()).delete(any(Episode.class));
    }

    // ------------------------------------

    @Test
    void testDeleteAllEpisodesFromSeason() {
        // Act
        episodeAdminService.deleteAllEpisodesFromSeason(seriesId, seasonId);

        // Assert
        verify(episodeRepository, times(1)).deleteAllBySeriesIdAndSeasonId(seriesId, seasonId);
    }

    // ------------------------------------

    @Test
    void testDeleteAllEpisodesFromSeries() {
        // Arrange
        doNothing().when(episodeRepository).deleteAllBySeriesId(seriesId);

        // Act
        episodeAdminService.deleteAllEpisodesFromSeries(seriesId);

        // Assert
        verify(episodeRepository, times(1)).deleteAllBySeriesId(seriesId);
    }

}
