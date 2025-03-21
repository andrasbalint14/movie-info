package com.andrasbalint.movie.model.external.omdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Detail result from OMDB
 *
 * @author abalint
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OMDBDetailResultDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5593726810534497332L;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Director")
    private String director;
}
