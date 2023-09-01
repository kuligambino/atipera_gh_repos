package com.atipera.kulig.task.domain;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
class ApiExceptionResponse {
    private final int status;
    private final String message;

    ApiExceptionResponse(HttpStatus responseCode, String whyHasItHappened) {
        this.status = responseCode.value();
        this.message = whyHasItHappened;
    }
}
