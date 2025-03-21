package com.andrasbalint.movie.service;

import com.andrasbalint.movie.model.MovieInfoDTO;
import com.andrasbalint.movie.model.enums.MovieDataSource;
import com.andrasbalint.movie.model.event.MovieSearchEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * Service for querying movie data from different sources
 *
 * @author abalint
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MovieInfoService {

    private final Map<String, MovieDataService> movieDataServices;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Get movie info from different sources
     *
     * @param movieTitle      title of movie
     * @param movieDataSource the source type
     * @return the result as {@link MovieInfoDTO}
     */
    public MovieInfoDTO getMovieInfo(String movieTitle, MovieDataSource movieDataSource) {
        log.info("Start querying from %s : %s".formatted(movieDataSource, movieTitle));
        eventPublisher.publishEvent(new MovieSearchEvent(this, movieTitle, movieDataSource));
        return getMovieDataService(movieDataSource)
                .map((MovieDataService movieDataService) -> movieDataService.queryMovieData(movieTitle))
                .orElseThrow(() -> new IllegalArgumentException("Unknown API: %s".formatted(movieDataSource)));
    }

    private Optional<MovieDataService> getMovieDataService(MovieDataSource movieDataSource) {
        return Optional.ofNullable(movieDataServices.get(movieDataSource.getServiceName()));
    }

}
