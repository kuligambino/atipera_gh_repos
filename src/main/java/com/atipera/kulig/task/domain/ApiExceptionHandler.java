package com.atipera.kulig.task.domain;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.NotAcceptableStatusException;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiExceptionResponse> handleNotFound(WebClientResponseException ex) {
        return ResponseEntity.ofNullable(new ApiExceptionResponse(NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(NotAcceptableStatusException.class)
    public ResponseEntity<ApiExceptionResponse> handleNotAcceptable(NotAcceptableStatusException ex) {
        return ResponseEntity
                .status(NOT_ACCEPTABLE)
                .contentType(APPLICATION_JSON)
                .body(new ApiExceptionResponse(NOT_ACCEPTABLE, ex.getBody().getDetail()));
    }
}
