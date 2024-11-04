package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.dto.EpisodeRequest;
import com.mariuszilinskas.vsp.contentservice.exception.EntityExistsException;
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
        when(episodeRepository.countBySeriesIdAndSeasonId(seriesId, seasonId)).thenReturn(0);
        when(seasonAdminService.updateSeasonEpisodeCount(seriesId, seasonId, 1)).thenReturn(1);


        // Act
        Episode response = episodeAdminService.createEpisodeInSeason(seriesId, seasonId, episodeRequest);

        // Assert
        assertNotNull(response);
        assertEquals(episode.getId(), response.getId());

        verify(episodeRepository, times(1)).existsBySeriesIdAndSeasonIdAndEpisodeNumber(seriesId, seasonId, episodeNumber);
        verify(episodeRepository, times(1)).save(captor.capture());
        verify(episodeRepository, times(1)).countBySeriesIdAndSeasonId(seriesId, seasonId);
        verify(seasonAdminService, times(1)).updateSeasonEpisodeCount(seriesId, seasonId, 1);

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
        verify(episodeRepository, never()).countBySeriesIdAndSeasonId(anyString(), anyString());
        verify(seasonAdminService, never()).updateSeasonEpisodeCount(anyString(), anyString(), anyInt());
    }

    // ------------------------------------

    @Test
    void testUpdateEpisodeInSeason() {
        ArgumentCaptor<Episode> captor = ArgumentCaptor.forClass(Episode.class);
        int episodeNumber = episodeRequest.episodeNumber();

        Episode existingEpisode = new Episode();
        existingEpisode.setId(episodeId);
        existingEpisode.setSeriesId(seriesId);
        existingEpisode.setSeasonId(seasonId);

        when(episodeRepository.findBySeriesIdAndSeasonIdAndEpisodeNumber(seriesId, seasonId, episodeNumber))
                .thenReturn(Optional.of(episode));
        when(episodeRepository.findByIdAndSeriesIdAndSeasonId(episodeId, seriesId, seasonId))
                .thenReturn(Optional.of(existingEpisode));
        when(episodeRepository.save(captor.capture())).thenReturn(existingEpisode);

        // Act
        episodeAdminService.updateEpisodeInSeason(seriesId, seasonId, episodeId, updatedEpisodeRequest);

        // Assert
        verify(episodeRepository, times(1)).findBySeriesIdAndSeasonIdAndEpisodeNumber(seriesId, seasonId, episodeNumber);
        verify(episodeRepository, times(1)).findByIdAndSeriesIdAndSeasonId(episodeId, seriesId, seasonId);
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
        int episodeNumber = episodeRequest.episodeNumber();

        Episode existingEpisode = new Episode();
        existingEpisode.setId("differentEpisodeId");
        existingEpisode.setSeriesId(seriesId);
        existingEpisode.setSeasonId(seasonId);
        existingEpisode.setEpisodeNumber(episodeNumber);

        when(episodeRepository.findBySeriesIdAndSeasonIdAndEpisodeNumber(seriesId, seasonId, episodeNumber))
                .thenReturn(Optional.of(existingEpisode));

        // Act & Assert
        assertThrows(EntityExistsException.class, () -> {
            episodeAdminService.updateEpisodeInSeason(seriesId, seasonId, episodeId, updatedEpisodeRequest);
        });

        verify(episodeRepository, times(1)).findBySeriesIdAndSeasonIdAndEpisodeNumber(seriesId, seasonId, episodeNumber);

        verify(episodeRepository, never()).findByIdAndSeriesIdAndSeasonId(anyString(), anyString(), anyString());
        verify(episodeRepository, never()).save(any(Episode.class));
    }

    // ------------------------------------

    @Test
    void testRemoveEpisodeFromSeason() {
        // Arrange

        // Act

        // Assert

    }

    // ------------------------------------

    @Test
    void testRemoveAllEpisodesFromSeason() {
        // Arrange

        // Act

        // Assert

    }

    // ------------------------------------

    @Test
    void testRemoveAllEpisodesFromSeries() {
        // Arrange

        // Act

        // Assert

    }

}
