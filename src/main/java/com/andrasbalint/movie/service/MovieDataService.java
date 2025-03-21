package com.andrasbalint.movie.service;

import com.andrasbalint.movie.exception.ExternalDataSourceException;
import com.andrasbalint.movie.model.MovieInfoDTO;
import com.andrasbalint.movie.model.enums.MovieDataSource;
import feign.FeignException;

/**
 * Base interface for movie data services
 *
 * @author abalint
 */
public interface MovieDataService {

    /**
     * Query movie info by seaerch term
     *
     * @param searchTerm search term text
     * @return the result as {@link MovieInfoDTO}
     */
    MovieInfoDTO queryMovieData(String searchTerm);

    /**
     * Get source type
     *
     * @return the source type
     */
    MovieDataSource getSourceType();

    /**
     * Common {@link FeignException} handler
     *
     * @param exception  the exception to be handled
     * @param searchTerm search term text
     */
    default ExternalDataSourceException handleFeignException(final FeignException exception, String searchTerm) {
        return new ExternalDataSourceException(exception.getMessage().formatted(searchTerm), getSourceType());
    }
}
