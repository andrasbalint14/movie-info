package com.andrasbalint.movie.service.cache;

import com.andrasbalint.movie.exception.ExternalDataSourceException;
import com.andrasbalint.movie.model.enums.MovieDataSource;
import com.andrasbalint.movie.model.external.moviedb.MovieDBCreditResultDTO;
import com.andrasbalint.movie.model.external.moviedb.MovieDBDetailResultDTO;
import com.andrasbalint.movie.model.external.moviedb.MovieDBSearchResponseDTO;
import com.andrasbalint.movie.service.feign.MovieDBFeignClient;
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
public class MovieDBCacheService {

    private static final String CACHE_SEARCH = "moviedb-search";
    private static final String CACHE_DETAIL = "moviedb-detail";
    private static final String CACHE_CREDITS = "moviedb-credits";

    private final MovieDBFeignClient movieDBFeignClient;

    /**
     * Cached search for movies
     * @param searchTerm
     * @param includeAdult
     * @return
     */
    @Cacheable(value = CACHE_SEARCH, key = "#searchTerm")
    public MovieDBSearchResponseDTO search(String searchTerm, Boolean includeAdult) {
        return Optional.ofNullable(movieDBFeignClient.search(searchTerm, includeAdult).getBody())
                .orElseThrow(() -> new ExternalDataSourceException("Error while searching %s".formatted(searchTerm),
                        MovieDataSource.THE_MOVIE_DB));
    }

    @Cacheable(value = CACHE_DETAIL, key = "#movieId")
    public MovieDBDetailResultDTO getDetail(Long movieId) {
        return Optional.ofNullable(movieDBFeignClient.getDetail(movieId).getBody())
                .orElseThrow(() -> new ExternalDataSourceException("Error while get detail",
                        MovieDataSource.THE_MOVIE_DB));
    }

    @Cacheable(value = CACHE_CREDITS, key = "#movieId")
    public MovieDBCreditResultDTO getCredits(Long movieId) {
        return Optional.ofNullable(movieDBFeignClient.getCredits(movieId).getBody())
                .orElseThrow(() -> new ExternalDataSourceException("Error while get credits",
                MovieDataSource.THE_MOVIE_DB));
    }
}
