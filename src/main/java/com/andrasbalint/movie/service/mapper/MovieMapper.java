package com.andrasbalint.movie.service.mapper;

import com.andrasbalint.movie.model.MovieDTO;
import com.andrasbalint.movie.model.external.moviedb.MovieDBDetailResultDTO;
import com.andrasbalint.movie.model.external.omdb.OMDBDetailResultDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Mapper class for constructing {@link MovieDTO} based on different sources
 *
 * @author abalint
 */
@Mapper(componentModel = "spring")
public interface MovieMapper {

    /**
     * Maps omdb detail to {@link MovieDTO}
     *
     * @param dto DTO to be mapped
     * @return the mapped {@link MovieDTO}
     */
    @Mapping(target = "director", qualifiedByName = "mapList")
    MovieDTO mapToMovieDTO(OMDBDetailResultDTO dto);

    /**
     * Maps movie db detail to {@link MovieDTO}
     *
     * @param dto DTO to be mapped
     * @return the mapped {@link MovieDTO}
     */
    @Mapping(target = "year", source = "dto.releaseDate", qualifiedByName = "getYearFromLocalDate")
    MovieDTO mapToMovieDTO(MovieDBDetailResultDTO dto, List<String> director);

    @Named("mapList")
    default List<String> mapList(String commaSeparatedList) {
        return commaSeparatedList != null ? Arrays.asList(commaSeparatedList.split(", ")) : null;
    }

    @Named("getYearFromLocalDate")
    default String getYearFromLocalDate(LocalDate date) {
        return String.valueOf(Optional.ofNullable(date).map(LocalDate::getYear).orElse(0));
    }
}
