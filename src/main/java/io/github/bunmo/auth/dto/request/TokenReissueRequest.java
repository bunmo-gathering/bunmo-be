package io.github.bunmo.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "jwt token 재발급 요청", description = "jwt token 재발급 요청")
public record TokenReissueRequest(
    @Schema(
        description = "refresh token",
        example = "로그인 후 전달 받은 refreshToken",
        type = "string"
    )
    @NotBlank(message = "refreshToken은 필수입니다")
    String refreshToken
) {

}
