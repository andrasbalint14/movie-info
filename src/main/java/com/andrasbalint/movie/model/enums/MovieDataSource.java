package com.andrasbalint.movie.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Type of movies sources
 *
 * @author abalint
 */
@Getter
@RequiredArgsConstructor
public enum MovieDataSource {

    OMDB_API("omdbService"),
    THE_MOVIE_DB("movieDbService");

    private final String serviceName;

}
