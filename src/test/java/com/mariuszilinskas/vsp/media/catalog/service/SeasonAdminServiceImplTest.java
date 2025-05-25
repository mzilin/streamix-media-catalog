package com.mariuszilinskas.vsp.media.catalog.service;

import com.mariuszilinskas.vsp.media.catalog.dto.SeasonRequest;
import com.mariuszilinskas.vsp.media.catalog.exception.EntityExistsException;
import com.mariuszilinskas.vsp.media.catalog.exception.ResourceNotFoundException;
import com.mariuszilinskas.vsp.media.catalog.model.document.Season;
import com.mariuszilinskas.vsp.media.catalog.repository.SeasonRepository;
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
        when(seasonRepository.save(captor.capture())).thenReturn(season);

        // Act
        Season response = seasonAdminService.createSeasonForSeries(seriesId, request);

        // Assert
        assertNotNull(request);
        assertEquals(season.getId(), response.getId());

        verify(seasonRepository, times(1)).existsBySeriesIdAndSeasonNumber(seriesId, seasonNumber);
        verify(seasonRepository, times(1)).save(captor.capture());

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

        verify(seasonRepository, never()).save(any(Season.class));
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
    void testDeleteSeasonFromSeries() {
        // Arrange
        when(seasonRepository.findByIdAndSeriesId(seasonId, seriesId)).thenReturn(Optional.of(season));
        doNothing().when(episodeAdminService).deleteAllEpisodesFromSeason(seriesId, seasonId);
        doNothing().when(seasonRepository).delete(season);

        // Act
        seasonAdminService.deleteSeasonFromSeries(seriesId, seasonId);

        // Assert
        verify(seasonRepository, times(1)).findByIdAndSeriesId(seasonId, seriesId);
        verify(episodeAdminService, times(1)).deleteAllEpisodesFromSeason(seriesId, seasonId);
        verify(seasonRepository, times(1)).delete(season);
    }

    @Test
    void testDeleteSeasonFromSeries_SeasonDoesntExist() {
        // Arrange
        when(seasonRepository.findByIdAndSeriesId(seasonId, seriesId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            seasonAdminService.deleteSeasonFromSeries(seriesId, seasonId);
        });

        // Assert
        verify(seasonRepository, times(1)).findByIdAndSeriesId(seasonId, seriesId);

        verify(episodeAdminService, never()).deleteAllEpisodesFromSeason(anyString(), anyString());
        verify(seasonRepository, never()).delete(any(Season.class));
    }

    // ------------------------------------

    @Test
    void testDeleteAllSeasonsFromSeries() {
        // Arrange
        doNothing().when(episodeAdminService).deleteAllEpisodesFromSeries(seriesId);
        doNothing().when(seasonRepository).deleteBySeriesId(seriesId);

        // Act
        seasonAdminService.deleteAllSeasonsFromSeries(seriesId);

        // Assert
        verify(episodeAdminService, times(1)).deleteAllEpisodesFromSeries(seriesId);
        verify(seasonRepository, times(1)).deleteBySeriesId(seriesId);
    }

}
