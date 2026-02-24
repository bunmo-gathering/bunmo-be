package io.github.bunmo.gathering.dto;

import io.github.bunmo.common.web.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GatheringResultCode implements ResultCode {
    GATHERING_CREATE_SUCCESS(HttpStatus.CREATED, "GATHERING_001", "모임 생성 성공"),
    GATHERING_LIST_SUCCESS(HttpStatus.OK, "GATHERING_002", "모임 목록 조회 성공"),
    GATHERING_DETAIL_SUCCESS(HttpStatus.OK, "GATHERING_003", "모임 상세 조회 성공");

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
