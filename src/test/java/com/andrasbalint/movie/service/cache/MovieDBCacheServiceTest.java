package com.andrasbalint.movie.service.cache;

import com.andrasbalint.movie.exception.ExternalDataSourceException;
import com.andrasbalint.movie.model.external.moviedb.MovieDBCreditResultDTO;
import com.andrasbalint.movie.model.external.moviedb.MovieDBDetailResultDTO;
import com.andrasbalint.movie.model.external.moviedb.MovieDBSearchResponseDTO;
import com.andrasbalint.movie.service.feign.MovieDBFeignClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieDBCacheServiceTest {

    @Mock
    private MovieDBFeignClient movieDBFeignClient;

    @InjectMocks
    private MovieDBCacheService movieDBCacheService;

    @Test
    void testSearch_shouldReturnSearchResponse() {
        // Arrange
        String searchTerm = "Inception";
        Boolean includeAdult = false;
        MovieDBSearchResponseDTO mockResponse = new MovieDBSearchResponseDTO(new ArrayList<>());
        when(movieDBFeignClient.search(searchTerm, includeAdult))
                .thenReturn(ResponseEntity.ok(mockResponse));

        // Act
        MovieDBSearchResponseDTO result = movieDBCacheService.search(searchTerm, includeAdult);

        // Assert
        assertNotNull(result);
        assertEquals(mockResponse, result);
    }

    @Test
    void testSearch_shouldThrowExternalDataSourceExceptionWhenNull() {
        // Arrange
        String searchTerm = "NonExistentMovie";
        Boolean includeAdult = false;
        when(movieDBFeignClient.search(searchTerm, includeAdult))
                .thenReturn(ResponseEntity.ok(null));

        // Act & Assert
        ExternalDataSourceException exception = assertThrows(ExternalDataSourceException.class, () ->
                movieDBCacheService.search(searchTerm, includeAdult));
        assertTrue(exception.getMessage().contains("Error while searching"));
    }

    @Test
    void testGetDetail_shouldReturnDetailResponse() {
        // Arrange
        Long movieId = 123L;
        MovieDBDetailResultDTO detailResponse = new MovieDBDetailResultDTO(1L, "title", LocalDate.now());
        when(movieDBFeignClient.getDetail(movieId))
                .thenReturn(ResponseEntity.ok(detailResponse));

        // Act
        MovieDBDetailResultDTO result = movieDBCacheService.getDetail(movieId);

        // Assert
        assertNotNull(result);
        assertEquals(detailResponse, result);
    }

    @Test
    void testGetDetail_shouldThrowExternalDataSourceExceptionWhenNull() {
        // Arrange
        Long movieId = 999L;
        when(movieDBFeignClient.getDetail(movieId))
                .thenReturn(ResponseEntity.ok(null));

        // Act & Assert
        ExternalDataSourceException exception = assertThrows(ExternalDataSourceException.class, () ->
                movieDBCacheService.getDetail(movieId));
        assertTrue(exception.getMessage().contains("Error while get detail"));
    }

    @Test
    void testGetCredits_shouldReturnCreditResponse() {
        // Arrange
        Long movieId = 456L;
        MovieDBCreditResultDTO creditResponse = new MovieDBCreditResultDTO(new ArrayList<>());
        when(movieDBFeignClient.getCredits(movieId))
                .thenReturn(ResponseEntity.ok(creditResponse));

        // Act
        MovieDBCreditResultDTO result = movieDBCacheService.getCredits(movieId);

        // Assert
        assertNotNull(result);
        assertEquals(creditResponse, result);
    }

    @Test
    void testGetCredits_shouldThrowExternalDataSourceExceptionWhenNull() {
        // Arrange
        Long movieId = 888L;
        when(movieDBFeignClient.getCredits(movieId))
                .thenReturn(ResponseEntity.ok(null));

        // Act & Assert
        ExternalDataSourceException exception = assertThrows(ExternalDataSourceException.class, () ->
                movieDBCacheService.getCredits(movieId));
        assertTrue(exception.getMessage().contains("Error while get credits"));
    }
}