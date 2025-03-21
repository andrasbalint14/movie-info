package com.andrasbalint.movie.messaging;

import com.andrasbalint.movie.model.enums.MovieDataSource;
import com.andrasbalint.movie.model.event.MovieSearchEvent;
import com.andrasbalint.movie.service.MovieStatisticService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MovieSearchEventListenerTest {

    @Mock
    private MovieStatisticService movieStatisticService;

    @InjectMocks
    private MovieSearchEventListener movieSearchEventListener;

    @Test
    void testHandleMovieSearchEvent_shouldRecordEvent() {
        // Arrange
        MovieSearchEvent event = new MovieSearchEvent(this, "Inception", MovieDataSource.THE_MOVIE_DB);

        // Act
        movieSearchEventListener.handleMovieSearchEvent(event);

        // Assert
        verify(movieStatisticService).recordEvent(event);
    }
}