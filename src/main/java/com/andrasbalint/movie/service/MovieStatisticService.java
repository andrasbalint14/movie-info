package com.andrasbalint.movie.service;

import com.andrasbalint.movie.model.event.MovieSearchEvent;
import com.andrasbalint.movie.repository.MovieInfoStatisticRepository;
import com.andrasbalint.movie.service.mapper.MovieStatisticMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for {@link com.andrasbalint.movie.entity.MovieInfoStatistic}
 *
 * @author abalint
 */
@RequiredArgsConstructor
@Service
public class MovieStatisticService {

    private final MovieInfoStatisticRepository movieInfoStatisticRepository;
    private final MovieStatisticMapper movieStatisticMapper;

    /**
     * Record statistics based on given event
     *
     * @param event the event
     */
    public void recordEvent(MovieSearchEvent event) {
        movieInfoStatisticRepository.saveAndFlush(movieStatisticMapper.toMovieInfoStatistic(event));
    }
}
