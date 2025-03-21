package com.andrasbalint.movie.service;


import com.andrasbalint.movie.entity.MovieInfoStatistic;
import com.andrasbalint.movie.model.event.MovieSearchEvent;
import com.andrasbalint.movie.repository.MovieInfoStatisticRepository;
import com.andrasbalint.movie.service.mapper.MovieStatisticMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.andrasbalint.movie.model.enums.MovieDataSource.OMDB_API;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MovieStatisticServiceTest {

    @Mock
    private MovieInfoStatisticRepository movieInfoStatisticRepository;

    @Spy
    private MovieStatisticMapper movieStatisticMapper = Mappers.getMapper(MovieStatisticMapper.class);

    @Captor
    private ArgumentCaptor<MovieInfoStatistic> statisticArgumentCaptor;

    @InjectMocks
    private MovieStatisticService movieStatisticService;

    @Test
    void recordEvent_shouldSaveStatistic() {
        // Arrange
        MovieSearchEvent event = new MovieSearchEvent(this, "Inception", OMDB_API);

        // Act
        movieStatisticService.recordEvent(event);

        // Assert
        verify(movieInfoStatisticRepository).saveAndFlush(statisticArgumentCaptor.capture());

        MovieInfoStatistic statisticResult  = statisticArgumentCaptor.getValue();
        assertEquals("Inception", statisticResult.getSearchTerm());
        assertEquals(OMDB_API, statisticResult.getDataSource());
        assertNotNull(statisticResult.getCreateTime());
    }
}