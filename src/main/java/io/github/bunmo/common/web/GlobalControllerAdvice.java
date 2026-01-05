package io.github.bunmo.common.web;

import io.github.bunmo.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        log.debug(e.getMessage());
        return ResponseEntity
                .status(e.errorCode().statusCode())
                .body(ApiResponse.error(e.errorCode()));
    }
}
