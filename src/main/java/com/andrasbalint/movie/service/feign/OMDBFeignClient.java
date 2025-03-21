package com.andrasbalint.movie.service.feign;

import com.andrasbalint.movie.configuration.feign.OmdbFeignConfiguration;
import com.andrasbalint.movie.model.external.omdb.OMDBDetailResultDTO;
import com.andrasbalint.movie.model.external.omdb.OMDBSearchResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client for OMDB API
 *
 * @author abalint
 */
@FeignClient(value = "omdbFeignClient", url = "${omdb.api.url}",
        configuration = OmdbFeignConfiguration.class)
public interface OMDBFeignClient {

    /**
     * Search endpoint
     *
     * @param searchTerm search term text
     * @return {@link ResponseEntity} of {@link OMDBSearchResponseDTO}
     */
    @GetMapping("/")
    ResponseEntity<OMDBSearchResponseDTO> search(@RequestParam("s") String searchTerm);

    /**
     * Get detail endpoint
     *
     * @param imdbId imdb id of the movie
     * @return {@link ResponseEntity} of {@link OMDBDetailResultDTO}
     */
    @GetMapping("/")
    ResponseEntity<OMDBDetailResultDTO> getDetail(@RequestParam("i") String imdbId);

}
