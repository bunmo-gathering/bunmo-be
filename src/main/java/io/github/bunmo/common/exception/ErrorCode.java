package io.github.bunmo.common.exception;

/**
* 각 도메인에서 구현
*/
public interface ErrorCode {
    int statusCode();
    String code();
    String message();
}
