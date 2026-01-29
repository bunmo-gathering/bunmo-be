package io.github.bunmo.auth.dto;

import io.github.bunmo.common.web.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Getter
@RequiredArgsConstructor
public enum AuthResultCode implements ResultCode {
    LOGIN_SUCCESS(OK, "AUTH_001", "로그인 성공"),
    SIGNUP_SUCCESS(CREATED, "AUTH_002", "회원가입 성공");

    private final HttpStatus statusCode;
    private final String code;
    private final String message;

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
