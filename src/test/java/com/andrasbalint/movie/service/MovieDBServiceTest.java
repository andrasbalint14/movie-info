package com.andrasbalint.movie.service;


import com.andrasbalint.movie.exception.ExternalDataSourceException;
import com.andrasbalint.movie.model.MovieInfoDTO;
import com.andrasbalint.movie.model.external.moviedb.CreditMemberDTO;
import com.andrasbalint.movie.model.external.moviedb.MovieDBCreditResultDTO;
import com.andrasbalint.movie.model.external.moviedb.MovieDBDetailResultDTO;
import com.andrasbalint.movie.model.external.moviedb.MovieDBSearchResponseDTO;
import com.andrasbalint.movie.service.cache.MovieDBCacheService;
import com.andrasbalint.movie.service.mapper.MovieMapper;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieDBServiceTest {

    @Mock
    private MovieDBCacheService movieDBCacheService;

    @Spy
    private MovieMapper movieMapper = Mappers.getMapper(MovieMapper.class);

    @InjectMocks
    private MovieDBService movieDBService;

    @Test
    void queryMovieData_shouldReturnMovieInfoDTO() {
        // Arrange
        String searchTerm = "Inception";
        MovieDBSearchResponseDTO.MovieDBMovieDTO movieDTO = new MovieDBSearchResponseDTO.MovieDBMovieDTO("Inception", 123L);
        MovieDBSearchResponseDTO searchResponseDTO = new MovieDBSearchResponseDTO(List.of(movieDTO));

        MovieDBDetailResultDTO detailDTO = new MovieDBDetailResultDTO(123L, "Inception", LocalDate.of(2020, 1, 1));
        CreditMemberDTO director = new CreditMemberDTO("Christopher Nolan", "Directing");
        MovieDBCreditResultDTO creditResultDTO = new MovieDBCreditResultDTO(List.of(director));

        when(movieDBCacheService.search(searchTerm, true)).thenReturn(searchResponseDTO);
        when(movieDBCacheService.getDetail(123L)).thenReturn(detailDTO);
        when(movieDBCacheService.getCredits(123L)).thenReturn(creditResultDTO);

        // Act
        MovieInfoDTO result = movieDBService.queryMovieData(searchTerm);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getMovies());
        assertEquals(1, result.getMovies().size());
        assertEquals("Inception", result.getMovies().getFirst().getTitle());
        assertEquals("2020", result.getMovies().getFirst().getYear());
        assertTrue(result.getMovies().getFirst().getDirector().contains("Christopher Nolan"));

        verify(movieDBCacheService).search(searchTerm, true);
        verify(movieDBCacheService).getDetail(123L);
        verify(movieDBCacheService).getCredits(123L);
        verify(movieMapper).mapToMovieDTO(detailDTO, List.of("Christopher Nolan"));
    }

    @Test
    void queryMovieData_shouldThrowException_whenFeignError() {
        // Arrange
        String searchTerm = "UnknownMovie";
        var exception = mock(FeignException.class);
        when(movieDBCacheService.search(searchTerm, true)).thenThrow(exception);
        when(exception.getMessage()).thenReturn("error message");

        // Act & Assert
        assertThrows(ExternalDataSourceException.class, () -> movieDBService.queryMovieData(searchTerm));
        verify(movieDBCacheService).search(searchTerm, true);
    }
}