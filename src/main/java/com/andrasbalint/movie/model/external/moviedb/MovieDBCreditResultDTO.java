package com.andrasbalint.movie.model.external.moviedb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MovieDBCreditResultDTO(List<CreditMemberDTO> cast) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1365829533241665708L;
}
