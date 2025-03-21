package com.andrasbalint.movie.controller;

import com.andrasbalint.movie.model.MovieInfoDTO;
import com.andrasbalint.movie.model.enums.MovieDataSource;
import com.andrasbalint.movie.service.MovieInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieInfoControllerTest {

    @Mock
    private MovieInfoService movieInfoService;

    @InjectMocks
    private MovieInfoController movieInfoController;

    @Test
    void testGetMovieInfo_shouldReturnMovieInfoDTO() {
        // Arrange
        String movieTitle = "Inception";
        MovieInfoDTO mockResponse = new MovieInfoDTO();

        when(movieInfoService.getMovieInfo(movieTitle, MovieDataSource.THE_MOVIE_DB)).thenReturn(mockResponse);

        // Act
        ResponseEntity<MovieInfoDTO> response = movieInfoController.getMovieInfo(movieTitle, MovieDataSource.THE_MOVIE_DB);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCode().value());
    }
}