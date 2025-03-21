package com.andrasbalint.movie.model.external.moviedb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreditMemberDTO(String name, @JsonProperty("known_for_department") String department)
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 2610471523267646804L;
}
