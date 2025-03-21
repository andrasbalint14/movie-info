package com.andrasbalint.movie.model.external.moviedb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MovieDBDetailResultDTO(Long id, String title, @JsonProperty("release_date") LocalDate releaseDate)
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 7869973706598353854L;

}
