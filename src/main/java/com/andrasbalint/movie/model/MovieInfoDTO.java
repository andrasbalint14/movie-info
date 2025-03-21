package com.andrasbalint.movie.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * The base movie info DTO representing the list of matching movies for the search
 *
 * @author abalint
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieInfoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1676403362752687051L;

    private List<MovieDTO> movies;
}
