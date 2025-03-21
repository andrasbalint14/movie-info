package com.andrasbalint.movie.service;


import com.andrasbalint.movie.exception.ExternalDataSourceException;
import com.andrasbalint.movie.model.MovieDTO;
import com.andrasbalint.movie.model.MovieInfoDTO;
import com.andrasbalint.movie.model.external.omdb.OMDBDetailResultDTO;
import com.andrasbalint.movie.model.external.omdb.OMDBSearchResponseDTO;
import com.andrasbalint.movie.service.cache.OMDBCacheService;
import com.andrasbalint.movie.service.mapper.MovieMapper;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.andrasbalint.movie.model.enums.MovieDataSource.OMDB_API;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OMDBServiceTest {

    @Mock
    private OMDBCacheService omdbCacheService;

    @Spy
    private MovieMapper movieMapper = Mappers.getMapper(MovieMapper.class);

    @InjectMocks
    private OMDBService omdbService;

    @Test
    void queryMovieData_shouldReturnMappedMovies() {
        // Arrange
        String searchTerm = "Inception";
        OMDBSearchResponseDTO.OmdbMovieDTO movieDTO = new OMDBSearchResponseDTO.OmdbMovieDTO();
        movieDTO.setImdbId("tt1375666");
        movieDTO.setTitle("Inception");
        movieDTO.setYear("2010");

        OMDBSearchResponseDTO searchResponse = new OMDBSearchResponseDTO();
        searchResponse.setSearch(List.of(movieDTO));

        OMDBDetailResultDTO detailDTO = new OMDBDetailResultDTO();
        detailDTO.setTitle("Inception");
        detailDTO.setYear("2010");
        detailDTO.setDirector("Christopher Nolan");

        when(omdbCacheService.search(searchTerm)).thenReturn(searchResponse);
        when(omdbCacheService.getDetail("tt1375666")).thenReturn(detailDTO);

        // Act
        MovieInfoDTO result = omdbService.queryMovieData(searchTerm);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getMovies());
        assertEquals(1, result.getMovies().size());
        MovieDTO mappedMovie = result.getMovies().getFirst();
        assertEquals("Inception", mappedMovie.getTitle());
        assertEquals("2010", mappedMovie.getYear());
        assertTrue(mappedMovie.getDirector().contains("Christopher Nolan"));

        // Verify mapping was called
        verify(movieMapper, times(1)).mapToMovieDTO(detailDTO);
    }

    @Test
    void queryMovieData_shouldReturnEmptyDTO_onFeignException() {
        // Arrange
        String searchTerm = "InvalidMovie";
        var exception = mock(FeignException.class);
        when(omdbCacheService.search(searchTerm)).thenThrow(exception);
        when(exception.getMessage()).thenReturn("error message");

        // Act & Assert
        assertThrows(ExternalDataSourceException.class, () -> omdbService.queryMovieData(searchTerm));
    }

    @Test
    void getSourceType_shouldReturnOMDB_API() {
        assertEquals(OMDB_API, omdbService.getSourceType());
    }
}