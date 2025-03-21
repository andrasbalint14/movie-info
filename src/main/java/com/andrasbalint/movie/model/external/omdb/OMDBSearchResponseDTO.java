package com.andrasbalint.movie.model.external.omdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Search result from OMDB
 *
 * @author abalint
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OMDBSearchResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7199253010695058216L;

    @JsonProperty("Search")
    private List<OmdbMovieDTO> search;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OmdbMovieDTO implements Serializable{

        @Serial
        private static final long serialVersionUID = 4861691638549229889L;

        @JsonProperty("Title")
        private String title;

        @JsonProperty("Year")
        private String year;

        @JsonProperty("imdbID")
        private String imdbId;
    }
}
