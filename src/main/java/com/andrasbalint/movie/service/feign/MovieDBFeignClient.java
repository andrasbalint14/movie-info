package com.andrasbalint.movie.service.feign;

import com.andrasbalint.movie.configuration.feign.MovieDBFeignConfiguration;
import com.andrasbalint.movie.model.external.moviedb.MovieDBCreditResultDTO;
import com.andrasbalint.movie.model.external.moviedb.MovieDBDetailResultDTO;
import com.andrasbalint.movie.model.external.moviedb.MovieDBSearchResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client for MovieDB API
 *
 * @author abalint
 */
@FeignClient(value = "movieDBFeignClient", url = "${moviedb.api.url}",
        configuration = MovieDBFeignConfiguration.class)
public interface MovieDBFeignClient {

    /**
     * Search endpoint
     *
     * @param searchTerm   search term text
     * @param includeAdult include adult flag
     * @return {@link ResponseEntity} of {@link MovieDBSearchResponseDTO}
     */
    @GetMapping("search/movie")
    ResponseEntity<MovieDBSearchResponseDTO> search(@RequestParam("query") String searchTerm,
                                                    @RequestParam("include_adult") Boolean includeAdult);

    /**
     * Get detail endpoint
     *
     * @param movieId   id of the movie
     * @return {@link ResponseEntity} of {@link MovieDBDetailResultDTO}
     */
    @GetMapping("movie/{movieId}")
    ResponseEntity<MovieDBDetailResultDTO> getDetail(@PathVariable("movieId") Long movieId);

    /**
     * Get credits endpoint
     *
     * @param movieId   id of the movie
     * @return {@link ResponseEntity} of {@link MovieDBCreditResultDTO}
     */
    @GetMapping("movie/{movieId}/credits")
    ResponseEntity<MovieDBCreditResultDTO> getCredits(@PathVariable("movieId") Long movieId);
}
