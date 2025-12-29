package io.github.bunmo.common.web;

import io.github.bunmo.common.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(e.errorCode().statusCode())
                .body(ApiResponse.error(e.errorCode()));
    }
}
