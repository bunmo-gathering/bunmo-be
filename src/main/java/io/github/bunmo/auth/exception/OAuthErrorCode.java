package io.github.bunmo.auth.exception;

import io.github.bunmo.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum OAuthErrorCode implements ErrorCode {
    KAKAO_USER_INFO_REQUEST_FAILED(BAD_GATEWAY, "OAUTH_001", "카카오 사용자 정보 요청에 실패했습니다"),
    INVALID_MEMBER(FORBIDDEN, "OAUTH_002", "유효하지 않은 사용자 입니다"),
    KAKAO_INVALID_PARAMETER(BAD_REQUEST, "OAUTH_003", "유효하지 않은 파라미터에 의해 카카오인증에 실패했습니다"),
    KAKAO_SERVER_ERROR(INTERNAL_SERVER_ERROR, "OAUTH_004", "카카오 서버 에러입니다")
    ;

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
