package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// prinde automat erorile din toata aplicatia si le formateaza frumos
@RestControllerAdvice
public class GlobalExceptionHandler {

    // prindem orice exceptie generala
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralError(Exception ex) {
        // returnam un mesaj curat si codul 500 (internal server error)
        return new ResponseEntity<>("a aparut o eroare la procesarea cererii: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}