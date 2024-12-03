package com.mariuszilinskas.vsp.contentservice.service;

import com.mariuszilinskas.vsp.contentservice.dto.SeasonRequest;
import com.mariuszilinskas.vsp.contentservice.exception.EntityExistsException;
import com.mariuszilinskas.vsp.contentservice.exception.ResourceNotFoundException;
import com.mariuszilinskas.vsp.contentservice.model.document.Season;
import com.mariuszilinskas.vsp.contentservice.repository.SeasonRepository;
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
public class SeasonAdminServiceImplTest {

    @Mock
    private SeasonRepository seasonRepository;

    @Mock
    private MediaAdminService mediaAdminService;

    @Mock
    private EpisodeAdminService episodeAdminService;

    @InjectMocks
    private SeasonAdminServiceImpl seasonAdminService;

    private final String seriesId = "series01";
    private final String seasonId = "season01";
    private final Season season = new Season();

    // ------------------------------------

    @BeforeEach
    void setUp() {
        season.setId(seasonId);
        season.setSeriesId(seriesId);
        season.setSeasonNumber(1);
        season.setRating(8.0);
        season.setEpisodeCount(1);
        season.setPoster("poster_url");
    }

    // ------------------------------------

    @Test
    void testCreateSeasonForSeries() {
        // Arrange
        ArgumentCaptor<Season> captor = ArgumentCaptor.forClass(Season.class);
        int seasonNumber = 2;
        SeasonRequest request = new SeasonRequest(seasonNumber, 9.0, "poster_url");

        when(seasonRepository.existsBySeriesIdAndSeasonNumber(seriesId, seasonNumber)).thenReturn(false);
        when(seasonRepository.countBySeriesId(seriesId)).thenReturn(1);
        when(seasonRepository.save(captor.capture())).thenReturn(season);
        when(mediaAdminService.updateSeriesSeasonCount(seriesId, 2)).thenReturn(seasonNumber);

        // Act
        Season response = seasonAdminService.createSeasonForSeries(seriesId, request);

        // Assert
        assertNotNull(request);
        assertEquals(season.getId(), response.getId());

        verify(seasonRepository, times(1)).existsBySeriesIdAndSeasonNumber(seriesId, seasonNumber);
        verify(seasonRepository, times(1)).countBySeriesId(seriesId);
        verify(seasonRepository, times(1)).save(captor.capture());
        verify(mediaAdminService, times(1)).updateSeriesSeasonCount(seriesId, seasonNumber);

        Season savedSeason = captor.getValue();
        assertEquals(seriesId, savedSeason.getSeriesId());
        assertEquals(request.rating(), savedSeason.getRating());
        assertEquals(request.poster(), savedSeason.getPoster());
    }

    @Test
    void testCreateSeasonForSeries_SeasonAlreadyExists() {
        // Arrange
        int seasonNumber = 1;
        SeasonRequest request = new SeasonRequest(seasonNumber, 9.0, "poster_url");

        when(seasonRepository.existsBySeriesIdAndSeasonNumber(seriesId, seasonNumber)).thenReturn(true);

        // Act & Assert
        assertThrows(EntityExistsException.class, () -> {
            seasonAdminService.createSeasonForSeries(seriesId, request);
        });

        // Assert
        verify(seasonRepository, times(1)).existsBySeriesIdAndSeasonNumber(seriesId, seasonNumber);

        verify(seasonRepository, never()).countBySeriesId(seriesId);
        verify(seasonRepository, never()).save(any(Season.class));
        verify(mediaAdminService, never()).updateSeriesSeasonCount(seriesId, seasonNumber);
    }

    // ------------------------------------

    @Test
    void testUpdateSeasonInSeries() {
        // Arrange
        ArgumentCaptor<Season> captor = ArgumentCaptor.forClass(Season.class);
        int seasonNumber = 1;
        SeasonRequest request = new SeasonRequest(seasonNumber, 9.0, "poster_url");

        when(seasonRepository.findByIdAndSeriesId(seasonId, seriesId)).thenReturn(Optional.of(season));
        when(seasonRepository.findBySeriesIdAndSeasonNumber(seriesId, seasonNumber)).thenReturn(Optional.of(season));
        when(seasonRepository.save(captor.capture())).thenReturn(season);

        // Act
        Season response = seasonAdminService.updateSeasonInSeries(seriesId, seasonId, request);

        // Assert
        assertNotNull(response);
        verify(seasonRepository, times(1)).findByIdAndSeriesId(seasonId, seriesId);
        verify(seasonRepository, times(1)).findBySeriesIdAndSeasonNumber(seriesId, seasonNumber);
        verify(seasonRepository, times(1)).save(captor.capture());

        Season savedSeason = captor.getValue();
        assertEquals(seasonId, savedSeason.getId());
        assertEquals(seriesId, savedSeason.getSeriesId());
        assertEquals(request.rating(), savedSeason.getRating());
        assertEquals(request.poster(), savedSeason.getPoster());
    }

    @Test
    void testUpdateSeasonInSeries_DuplicateSeasonNumber() {
        // Arrange
        int seasonNumber = 2;

        Season duplicateSeason = new Season();
        duplicateSeason.setId("some-id");
        duplicateSeason.setSeriesId(seriesId);
        duplicateSeason.setSeasonNumber(seasonNumber);

        SeasonRequest request = new SeasonRequest(seasonNumber, 9.0, "poster_url");
        when(seasonRepository.findByIdAndSeriesId(seasonId, seriesId)).thenReturn(Optional.of(season));
        when(seasonRepository.findBySeriesIdAndSeasonNumber(seriesId, seasonNumber)).thenReturn(Optional.of(duplicateSeason));

        // Act & Assert
        assertThrows(EntityExistsException.class, () -> {
            seasonAdminService.updateSeasonInSeries(seriesId, seasonId, request);
        });

        // Assert
        verify(seasonRepository, times(1)).findByIdAndSeriesId(seasonId, seriesId);
        verify(seasonRepository, times(1)).findBySeriesIdAndSeasonNumber(seriesId, seasonNumber);

        verify(seasonRepository, never()).save(any(Season.class));
    }

    @Test
    void testUpdateSeasonInSeries_SeasonDoesntExist() {
        // Arrange
        int seasonNumber = 2;

        SeasonRequest request = new SeasonRequest(seasonNumber, 9.0, "poster_url");
        when(seasonRepository.findByIdAndSeriesId(seasonId, seriesId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            seasonAdminService.updateSeasonInSeries(seriesId, seasonId, request);
        });

        // Assert
        verify(seasonRepository, times(1)).findByIdAndSeriesId(seasonId, seriesId);

        verify(seasonRepository, never()).findBySeriesIdAndSeasonNumber(anyString(), anyInt());
        verify(seasonRepository, never()).save(any(Season.class));
    }

    // ------------------------------------

    @Test
    void testUpdateSeasonEpisodeCount() {
        // Arrange
        ArgumentCaptor<Season> captor = ArgumentCaptor.forClass(Season.class);

        when(seasonRepository.findByIdAndSeriesId(seasonId, seriesId)).thenReturn(Optional.of(season));
        when(seasonRepository.save(captor.capture())).thenReturn(season);

        // Act
        int response = seasonAdminService.updateSeasonEpisodeCount(seriesId, seasonId, 0);

        // Assert
        verify(seasonRepository, times(1)).findByIdAndSeriesId(seasonId, seriesId);
        verify(seasonRepository, times(1)).save(captor.capture());

        assertEquals(0, response);
        Season savedSeason = captor.getValue();
        assertEquals(0, savedSeason.getEpisodeCount());
    }

    @Test
    void testUpdateSeasonEpisodeCount_NegativeCountPassed_ZeroReturned() {
        // Arrange
        ArgumentCaptor<Season> captor = ArgumentCaptor.forClass(Season.class);

        when(seasonRepository.findByIdAndSeriesId(seasonId, seriesId)).thenReturn(Optional.of(season));
        when(seasonRepository.save(captor.capture())).thenReturn(season);

        // Act
        int response = seasonAdminService.updateSeasonEpisodeCount(seriesId, seasonId, -1);

        // Assert
        verify(seasonRepository, times(1)).findByIdAndSeriesId(seasonId, seriesId);
        verify(seasonRepository, times(1)).save(captor.capture());

        assertEquals(0, response);
        Season savedSeason = captor.getValue();
        assertEquals(0, savedSeason.getEpisodeCount());
    }

    // ------------------------------------

    @Test
    void testRemoveSeasonFromSeries() {
        // Arrange
        when(seasonRepository.findByIdAndSeriesId(seasonId, seriesId)).thenReturn(Optional.of(season));
        when(seasonRepository.countBySeriesId(seriesId)).thenReturn(1);
        when(mediaAdminService.updateSeriesSeasonCount(seriesId, 0)).thenReturn(0);
        doNothing().when(episodeAdminService).removeAllEpisodesFromSeason(seriesId, seasonId);

        // Act
        seasonAdminService.removeSeasonFromSeries(seriesId, seasonId);

        // Assert
        verify(seasonRepository, times(1)).findByIdAndSeriesId(seasonId, seriesId);
        verify(seasonRepository, times(1)).countBySeriesId(seriesId);
        verify(mediaAdminService, times(1)).updateSeriesSeasonCount(seriesId, 0);
        verify(episodeAdminService, times(1)).removeAllEpisodesFromSeason(seriesId, seasonId);
    }

    @Test
    void testRemoveSeasonFromSeries_SeasonDoesntExist() {
        // Arrange
        when(seasonRepository.findByIdAndSeriesId(seasonId, seriesId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            seasonAdminService.removeSeasonFromSeries(seriesId, seasonId);
        });

        // Assert
        verify(seasonRepository, times(1)).findByIdAndSeriesId(seasonId, seriesId);

        verify(seasonRepository, never()).countBySeriesId(anyString());
        verify(mediaAdminService, never()).updateSeriesSeasonCount(anyString(), anyInt());
        verify(episodeAdminService, never()).removeAllEpisodesFromSeason(anyString(), anyString());
    }

    // ------------------------------------

    @Test
    void testRemoveAllSeasonsFromSeries() {
        // Arrange
        doNothing().when(seasonRepository).deleteBySeriesId(seriesId);
        doNothing().when(episodeAdminService).removeAllEpisodesFromSeries(seriesId);

        // Act
        seasonAdminService.removeAllSeasonsFromSeries(seriesId);

        // Assert
        verify(seasonRepository, times(1)).deleteBySeriesId(seriesId);
        verify(episodeAdminService, times(1)).removeAllEpisodesFromSeries(seriesId);
    }

}
