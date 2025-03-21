package com.andrasbalint.movie.messaging;

import com.andrasbalint.movie.model.event.MovieSearchEvent;
import com.andrasbalint.movie.service.MovieStatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Listener for movie search events
 *
 * @author abalint
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MovieSearchEventListener {

    private final MovieStatisticService movieStatisticService;

    /**
     * Listen for events and saves records
     *
     * @param event the event
     */
    @Async
    @EventListener
    public void handleMovieSearchEvent(MovieSearchEvent event) {
        log.info("Registered event: {}", event);
        movieStatisticService.recordEvent(event);
    }
}
