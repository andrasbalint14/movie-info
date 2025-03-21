package com.andrasbalint.movie.controller;

import com.andrasbalint.movie.model.MovieInfoDTO;
import com.andrasbalint.movie.model.enums.MovieDataSource;
import com.andrasbalint.movie.service.MovieInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for movie info
 *
 * @author abalint
 */
@RestController
@RequiredArgsConstructor
public class MovieInfoController implements MovieInfoAPI {

    private final MovieInfoService movieInfoService;

    @Override
    public ResponseEntity<MovieInfoDTO> getMovieInfo(String movieTitle, MovieDataSource apiName) {
        return ResponseEntity.ok(movieInfoService.getMovieInfo(movieTitle, apiName));
    }
}
