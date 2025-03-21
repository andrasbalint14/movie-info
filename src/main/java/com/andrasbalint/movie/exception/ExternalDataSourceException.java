package com.andrasbalint.movie.exception;

import com.andrasbalint.movie.model.enums.MovieDataSource;
import lombok.Getter;

/**
 * Custom exception for external source errors
 *
 * @author abalint
 */
@Getter
public class ExternalDataSourceException extends RuntimeException {

    private MovieDataSource movieDataSource;

    public ExternalDataSourceException(String message) {
        super(message);
    }

    public ExternalDataSourceException(String message, MovieDataSource movieDataSource) {
        super(message);
        this.movieDataSource = movieDataSource;
    }
}
