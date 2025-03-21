package com.andrasbalint.movie.model.event;

import com.andrasbalint.movie.model.enums.MovieDataSource;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.io.Serializable;

@Getter
@ToString
public class MovieSearchEvent extends ApplicationEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1348188863567038981L;

    private final String searchTerm;
    private final MovieDataSource dataSource;

    public MovieSearchEvent(Object source, String searchTerm, MovieDataSource dataSource) {
        super(source);
        this.searchTerm = searchTerm;
        this.dataSource = dataSource;
    }
}
