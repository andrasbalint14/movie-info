package com.andrasbalint.movie.model.external.moviedb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MovieDBSearchResponseDTO(List<MovieDBMovieDTO> results) implements Serializable {

    @Serial
    private static final long serialVersionUID = 2817069444797102233L;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MovieDBMovieDTO(String title, Long id) implements Serializable {
        @Serial
        private static final long serialVersionUID = -162214815343668962L;
    }
}
