package com.andrasbalint.movie.configuration.web;

import com.andrasbalint.movie.exception.ExternalDataSourceException;
import com.andrasbalint.movie.model.ErrorDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Customer rest exception handler to give specific {@link HttpStatus}
 *
 * @author abalint
 */
@Slf4j
@ControllerAdvice
public class CustomerRestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles {@link ExternalDataSourceException} and return 502 status
     *
     * @param e the error
     * @return error response
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ExternalDataSourceException.class})
    public ResponseEntity<ErrorDetailDTO> handleExternalDataSourceException(ExternalDataSourceException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ErrorDetailDTO(e.getMessage()));
    }
}
