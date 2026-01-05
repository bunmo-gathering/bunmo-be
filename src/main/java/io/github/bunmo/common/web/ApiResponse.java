package io.github.bunmo.common.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.bunmo.common.exception.ErrorCode;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"code", "message", "data", "errors"})
record ApiResponse<T>(
        String code,
        String message,
        T data,
        List<FieldError> errors
) {
    public record FieldError(String field, String message) {}

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("OK", "success", data, null);
    }

    public static ApiResponse<Void> error(ErrorCode errorCode) {
        return new ApiResponse(errorCode.code(), errorCode.message(), null, null);
    }
}
