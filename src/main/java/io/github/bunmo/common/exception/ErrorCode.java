package io.github.bunmo.common.exception;

import org.springframework.http.HttpStatus;

/**
* 각 도메인에서 구현
*/
public interface ErrorCode {
    HttpStatus statusCode();
    String code();
    String message();
}
