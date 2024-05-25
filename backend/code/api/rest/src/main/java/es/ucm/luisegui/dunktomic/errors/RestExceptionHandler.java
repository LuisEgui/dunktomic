package es.ucm.luisegui.dunktomic.errors;

import es.ucm.luisegui.dunktomic.domain.exceptions.ClubNotFoundException;
import es.ucm.luisegui.dunktomic.domain.exceptions.DomainException;
import es.ucm.luisegui.dunktomic.domain.exceptions.EntityNotFoundException;
import es.ucm.luisegui.dunktomic.rest.dtos.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClubNotFoundException.class)
    public ResponseEntity<Error> handle(ClubNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(RestError.NOT_FOUND.error(ex.getCode(), ex.getDescription()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Error> handle(EntityNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(RestError.NOT_FOUND.error(ex.getMessage()));
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Error> handle(DomainException ex) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(RestError.INTERNAL_SERVER_ERROR.error(ex.getCode(), ex.getDescription()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handle(Exception ex) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(RestError.INTERNAL_SERVER_ERROR.error());
    }
}
