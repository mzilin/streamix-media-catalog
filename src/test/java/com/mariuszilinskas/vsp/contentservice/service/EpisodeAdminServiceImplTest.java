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

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.WeakHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
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
        // Arrange


    }

    @Test
    void testUpdateEpisodeInSeason_DuplicateEpisodeNumber() {
        // Arrange
        String newEpisodeId = "AnotherId";
        Episode anotherEpisode = new Episode();
        anotherEpisode.setId(newEpisodeId);
        anotherEpisode.setSeriesId(seriesId);
        anotherEpisode.setSeasonId(seasonId);
        anotherEpisode.setEpisodeNumber(episodeRequest.episodeNumber());

        when(episodeRepository.findBySeriesIdAndSeasonIdAndEpisodeNumber(seriesId, seasonId, episodeRequest.episodeNumber()))
                .thenReturn(Optional.of(episode));

        // Act & Assert
        assertThrows(EntityExistsException.class, () -> {
            episodeAdminService.updateEpisodeInSeason(seriesId, seasonId, newEpisodeId, episodeRequest);
        });

        verify(episodeRepository, times(1)).findBySeriesIdAndSeasonIdAndEpisodeNumber(seriesId, seasonId, episodeRequest.episodeNumber());

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
