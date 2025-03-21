package com.andrasbalint.movie.service;

import com.andrasbalint.movie.model.MovieInfoDTO;
import com.andrasbalint.movie.model.enums.MovieDataSource;
import com.andrasbalint.movie.model.event.MovieSearchEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieInfoServiceTest {

    @Mock
    private Map<String, MovieDataService> movieDataServices;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private MovieDataService movieDataService;

    @InjectMocks
    private MovieInfoService movieInfoService;

    @Test
    void getMovieInfo_shouldReturnMovieInfoDTO() {
        // Arrange
        String movieTitle = "Inception";
        MovieDataSource dataSource = MovieDataSource.OMDB_API;
        MovieInfoDTO expectedMovieInfo = new MovieInfoDTO();

        when(movieDataServices.get(dataSource.getServiceName())).thenReturn(movieDataService);
        when(movieDataService.queryMovieData(movieTitle)).thenReturn(expectedMovieInfo);

        // Act
        MovieInfoDTO result = movieInfoService.getMovieInfo(movieTitle, dataSource);

        // Assert
        assertNotNull(result);
        assertEquals(expectedMovieInfo, result);
        verify(eventPublisher).publishEvent(any(MovieSearchEvent.class));
        verify(movieDataService).queryMovieData(movieTitle);
    }

    @Test
    void getMovieInfo_shouldThrowException_whenUnknownDataSource() {
        // Arrange
        MovieDataSource unknownDataSource = MovieDataSource.OMDB_API;

        when(movieDataServices.get(unknownDataSource.getServiceName())).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> movieInfoService.getMovieInfo("UnknownMovie", unknownDataSource));

        assertTrue(exception.getMessage().contains("Unknown API"));
        verify(eventPublisher).publishEvent(any(MovieSearchEvent.class));
    }
}