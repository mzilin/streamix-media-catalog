package com.mariuszilinskas.vsp.contentservice.service;

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

        // Act

        // Assert
    }

    // ------------------------------------

    @Test
    void testUpdateSeasonInSeries() {
        // Arrange

        // Act

        // Assert
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
