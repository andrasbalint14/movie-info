package com.andrasbalint.movie.controller;

import com.andrasbalint.movie.model.MovieInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.andrasbalint.movie.model.enums.MovieDataSource;

/**
 * API for movie info
 *
 * @author abalint
 */
public interface MovieInfoAPI {

    /**
     * Get movie info based on search term
     *
     * @param movieTitle title of movie
     * @param apiName    name of the external api
     * @return {@link MovieInfoDTO}
     */
    @GetMapping("/movies/{movieTitle}")
    ResponseEntity<MovieInfoDTO> getMovieInfo(@PathVariable String movieTitle,
                                              @RequestParam("api") MovieDataSource apiName);

}
