package com.andrasbalint.movie.service.cache;

import com.andrasbalint.movie.exception.ExternalDataSourceException;
import com.andrasbalint.movie.model.enums.MovieDataSource;
import com.andrasbalint.movie.model.external.omdb.OMDBDetailResultDTO;
import com.andrasbalint.movie.model.external.omdb.OMDBSearchResponseDTO;
import com.andrasbalint.movie.service.feign.OMDBFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for caching external calls from Movie DB
 *
 * @author abalint
 */
@Service
@RequiredArgsConstructor
public class OMDBCacheService {

    private static final String CACHE_SEARCH = "omdb-search";
    private static final String CACHE_DETAIL = "omdb-detail";

    private final OMDBFeignClient omdbFeignClient;

    /**
     * Cached search for movies extracted from ResponseEntity
     *
     * @param searchTerm the search term
     * @return the extracted {@link OMDBSearchResponseDTO}
     */
    @Cacheable(value = CACHE_SEARCH, key = "#searchTerm")
    public OMDBSearchResponseDTO search(String searchTerm) {
        return Optional.ofNullable(omdbFeignClient.search(searchTerm).getBody())
                .orElseThrow(() -> new ExternalDataSourceException("Error while searching %s".formatted(searchTerm),
                        MovieDataSource.OMDB_API));
    }

    /**
     * Cached get detail by imdb id extracted from ResponseEntity
     *
     * @param imdbId id of the movie in IMDB
     * @return the extracted {@link OMDBDetailResultDTO}
     */
    @Cacheable(value = CACHE_DETAIL, key = "#imdbId")
    public OMDBDetailResultDTO getDetail(String imdbId) {
        return Optional.ofNullable(omdbFeignClient.getDetail(imdbId).getBody())
                .orElseThrow(() -> new ExternalDataSourceException("Error while get detail %s".formatted(imdbId),
                        MovieDataSource.OMDB_API));
    }
}
