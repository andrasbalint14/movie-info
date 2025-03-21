package com.andrasbalint.movie.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
@Data
@AllArgsConstructor
public class ErrorDetailDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3973458312409801064L;

    private String message;
}
