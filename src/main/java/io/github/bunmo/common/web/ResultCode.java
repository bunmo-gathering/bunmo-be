package io.github.bunmo.common.web;

import org.springframework.http.HttpStatus;

public interface ResultCode {
    HttpStatus statusCode();
    String code();
    String message();
}
