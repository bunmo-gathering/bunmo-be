package io.github.bunmo.security.exception;

import io.github.bunmo.common.exception.ErrorCode;

public class AuthException extends RuntimeException {
    private final ErrorCode errorCode;

    public AuthException(ErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }

    public ErrorCode errorCode() {
        return errorCode;
    }
}
