package com.andrasbalint.movie.service;

import com.andrasbalint.movie.model.MovieInfoDTO;
import com.andrasbalint.movie.model.enums.MovieDataSource;
import com.andrasbalint.movie.model.external.omdb.OMDBDetailResultDTO;
import com.andrasbalint.movie.model.external.omdb.OMDBSearchResponseDTO;
import com.andrasbalint.movie.service.cache.OMDBCacheService;
import com.andrasbalint.movie.service.mapper.MovieMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

/**
 * Service for querying data from OMDB API
 *
 * @author abalint
 */
@Service("omdbService")
@RequiredArgsConstructor
@Slf4j
public class OMDBService implements MovieDataService {

    private final OMDBCacheService service;
    private final MovieMapper movieMapper;

    @Override
    public MovieInfoDTO queryMovieData(String searchTerm) {
        try {
            var movieResult = queryAllMovies(service.search(searchTerm))
                    .map(movieMapper::mapToMovieDTO).toList();
            return new MovieInfoDTO(movieResult);
        } catch (FeignException exception) {
            throw handleFeignException(exception, searchTerm);
        }
    }

    @Override
    public MovieDataSource getSourceType() {
        return MovieDataSource.OMDB_API;
    }

    private Stream<OMDBDetailResultDTO> queryAllMovies(OMDBSearchResponseDTO dto) {
        return dto.getSearch()
                .stream()
                .map(OMDBSearchResponseDTO.OmdbMovieDTO::getImdbId)
                .map(service::getDetail);
    }
}
