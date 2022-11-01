package br.com.alura.comex.config;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmptyResultDataAccessHandler {
    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<?> handle() {
        return ResponseEntity.notFound().build();
    }
}
