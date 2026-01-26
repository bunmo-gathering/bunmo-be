package io.github.bunmo.member.exception;

import io.github.bunmo.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public enum MemberErrorCode implements ErrorCode {
    MEMBER_NOT_FOUND(NOT_FOUND, "MEMBER_001", "존재하지 않는 회원입니다");

    private final HttpStatus statusCode;
    private final String code;
    private final String message;

    MemberErrorCode(HttpStatus statusCode, String code, String message) {
        this.statusCode = statusCode;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus statusCode() {
        return statusCode;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
