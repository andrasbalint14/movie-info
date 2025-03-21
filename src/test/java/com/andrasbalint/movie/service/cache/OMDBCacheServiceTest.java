package com.andrasbalint.movie.service.cache;

import com.andrasbalint.movie.exception.ExternalDataSourceException;
import com.andrasbalint.movie.model.enums.MovieDataSource;
import com.andrasbalint.movie.model.external.omdb.OMDBDetailResultDTO;
import com.andrasbalint.movie.model.external.omdb.OMDBSearchResponseDTO;
import com.andrasbalint.movie.service.feign.OMDBFeignClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OMDBCacheServiceTest {

    @Mock
    private OMDBFeignClient omdbFeignClient;

    @InjectMocks
    private OMDBCacheService omdbCacheService;

    @Test
    void testSearch_shouldReturnSearchResponse() {
        // Arrange
        String searchTerm = "Interstellar";
        OMDBSearchResponseDTO mockResponse = new OMDBSearchResponseDTO();
        when(omdbFeignClient.search(searchTerm)).thenReturn(ResponseEntity.ok(mockResponse));

        // Act
        OMDBSearchResponseDTO result = omdbCacheService.search(searchTerm);

        // Assert
        assertNotNull(result);
        assertEquals(mockResponse, result);
    }

    @Test
    void testSearch_shouldThrowExternalDataSourceExceptionWhenNull() {
        // Arrange
        String searchTerm = "UnknownMovie";
        when(omdbFeignClient.search(searchTerm)).thenReturn(ResponseEntity.ok(null));

        // Act & Assert
        ExternalDataSourceException exception = assertThrows(ExternalDataSourceException.class, () ->
                omdbCacheService.search(searchTerm));
        assertTrue(exception.getMessage().contains("Error while searching"));
        assertEquals(MovieDataSource.OMDB_API, exception.getMovieDataSource());
    }

    @Test
    void testGetDetail_shouldReturnDetailResponse() {
        // Arrange
        String imdbId = "tt0816692";
        OMDBDetailResultDTO mockDetailResponse = new OMDBDetailResultDTO();
        when(omdbFeignClient.getDetail(imdbId)).thenReturn(ResponseEntity.ok(mockDetailResponse));

        // Act
        OMDBDetailResultDTO result = omdbCacheService.getDetail(imdbId);

        // Assert
        assertNotNull(result);
        assertEquals(mockDetailResponse, result);
    }

    @Test
    void testGetDetail_shouldThrowExternalDataSourceExceptionWhenNull() {
        // Arrange
        String imdbId = "invalid-id";
        when(omdbFeignClient.getDetail(imdbId)).thenReturn(ResponseEntity.ok(null));

        // Act & Assert
        ExternalDataSourceException exception = assertThrows(ExternalDataSourceException.class, () ->
                omdbCacheService.getDetail(imdbId));
        assertTrue(exception.getMessage().contains("Error while get detail"));
        assertEquals(MovieDataSource.OMDB_API, exception.getMovieDataSource());
    }
}