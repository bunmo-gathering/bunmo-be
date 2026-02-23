package io.github.bunmo.gathering.exception;

import io.github.bunmo.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GatheringErrorCode implements ErrorCode {
    GATHERING_NOT_FOUND(HttpStatus.NOT_FOUND, "GATHERING_001", "모임을 찾을 수 없습니다."),
    PRODUCT_LINK_REQUIRED(HttpStatus.BAD_REQUEST, "GATHERING_002", "쇼핑몰 모임은 상품 링크가 필수입니다.");

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
