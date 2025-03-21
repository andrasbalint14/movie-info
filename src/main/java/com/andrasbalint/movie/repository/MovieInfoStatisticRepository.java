package com.andrasbalint.movie.repository;

import com.andrasbalint.movie.entity.MovieInfoStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link MovieInfoStatistic}
 *
 * @author abalint
 */
@Repository
public interface MovieInfoStatisticRepository extends JpaRepository<MovieInfoStatistic, Long> {
}
