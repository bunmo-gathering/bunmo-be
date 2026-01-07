package io.github.bunmo.security.exception;

import io.github.bunmo.common.exception.ErrorCode;

public enum AuthErrorCode implements ErrorCode {
    INVALID_JWT_TOKEN(401, "UNAUTHORIZED_001", "유효하지 않은 토큰입니다"),
    EXPIRED_JWT_TOKEN(401, "UNAUTHORIZED_002", "만료된 토큰입니다"),
    UNSUPPORTED_JWT_TOKEN(401, "UNAUTHORIZED_003", "지원하지 않는 토큰입니다"),
    ACCESS_DENIED(401, "UNAUTHORIZED_004", "접근이 거부되었습니다"),
    UNAUTHORIZED(401, "UNAUTHORIZED_005", "인증이 필요합니다"),
    ;

    private final int statusCode;
    private final String code;
    private final String message;

    AuthErrorCode(int statusCode, String code, String message) {
        this.statusCode = statusCode;
        this.code = code;
        this.message = message;
    }

    @Override
    public int statusCode() {
        return this.statusCode;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }
}
