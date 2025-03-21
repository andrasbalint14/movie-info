package com.andrasbalint.movie.service;

import com.andrasbalint.movie.model.MovieDTO;
import com.andrasbalint.movie.model.MovieInfoDTO;
import com.andrasbalint.movie.model.enums.MovieDataSource;
import com.andrasbalint.movie.model.external.moviedb.CreditMemberDTO;
import com.andrasbalint.movie.model.external.moviedb.MovieDBDetailResultDTO;
import com.andrasbalint.movie.model.external.moviedb.MovieDBSearchResponseDTO;
import com.andrasbalint.movie.service.cache.MovieDBCacheService;
import com.andrasbalint.movie.service.mapper.MovieMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for querying data from Movie DB API
 *
 * @author abalint
 */
@Service("movieDbService")
@RequiredArgsConstructor
public class MovieDBService implements MovieDataService {

    private static final String DIRECTOR = "Directing";

    private final MovieDBCacheService service;
    private final MovieMapper movieMapper;

    @Override
    public MovieInfoDTO queryMovieData(String searchTerm) {
        try {
            List<MovieDBSearchResponseDTO.MovieDBMovieDTO> searchResult = service.search(searchTerm, true).results();

            List<MovieDTO> movieResult = searchResult.stream().map(MovieDBSearchResponseDTO.MovieDBMovieDTO::id)
                    .map(service::getDetail)
                    .map((MovieDBDetailResultDTO detail) ->
                            movieMapper.mapToMovieDTO(detail, getDirectorsForMovie(detail.id()))).toList();

            return new MovieInfoDTO(movieResult);
        } catch (FeignException exception) {
            throw handleFeignException(exception, searchTerm);
        }
    }

    @Override
    public MovieDataSource getSourceType() {
        return MovieDataSource.THE_MOVIE_DB;
    }

    private List<String> getDirectorsForMovie(Long id) {
        List<CreditMemberDTO> allCasts = service.getCredits(id).cast();
        return allCasts.stream().filter(MovieDBService::isDirector).map(CreditMemberDTO::name).toList();
    }

    private static boolean isDirector(CreditMemberDTO creditMemberDTO) {
        return DIRECTOR.equals(creditMemberDTO.department());
    }

}
