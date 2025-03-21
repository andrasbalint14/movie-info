package com.andrasbalint.movie.entity;

import com.andrasbalint.movie.model.enums.MovieDataSource;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Entity to store movie querying statistics
 *
 * @author abalint
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieInfoStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String searchTerm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovieDataSource dataSource;

    @Column(nullable = false)
    private LocalDateTime createTime;
}
