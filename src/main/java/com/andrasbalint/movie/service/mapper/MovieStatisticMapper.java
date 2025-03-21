package com.andrasbalint.movie.service.mapper;

import com.andrasbalint.movie.entity.MovieInfoStatistic;
import com.andrasbalint.movie.model.event.MovieSearchEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

/**
 * Mapper for {@link MovieInfoStatistic}
 *
 * @author abalint
 */
@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface MovieStatisticMapper {
    /**
     * Maps {@link MovieSearchEvent} to {@link MovieInfoStatistic}
     *
     * @param event event to be mapped
     * @return the mapped {@link MovieInfoStatistic}
     */
    @Mapping(target = "createTime", expression = "java(LocalDateTime.now())")
    MovieInfoStatistic toMovieInfoStatistic(MovieSearchEvent event);
}
