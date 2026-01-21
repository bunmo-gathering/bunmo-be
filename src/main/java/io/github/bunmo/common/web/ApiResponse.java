package io.github.bunmo.common.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.bunmo.common.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"code", "message", "data", "errors"})
public class ApiResponse<T> {
    private final String code;
    private final String message;
    private final T data;
    private final List<FieldError> errors;

    public record FieldError(String field, String message) {}

    public static <T> ApiResponse<T> success(ResultCode resultCode, T data) {
        return new ApiResponse<>(resultCode.code(), resultCode.message(), data, null);
    }

    public static ApiResponse<Void> error(ErrorCode errorCode) {
        return new ApiResponse(errorCode.code(), errorCode.message(), null, null);
    }
}
