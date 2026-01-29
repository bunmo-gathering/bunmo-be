package io.github.bunmo.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "카카오 로그인 요청", description = "카카오 로그인 요청")
public record KakaoLoginRequest(
        @Schema(
                description = "카카오 인가 코드",
                example = "카카오 로그인 후 callback으로 전달 받은 code",
                type = "string"
        )
        @NotBlank(message = "인가 코드는 필수입니다")
        String code
) {
}
