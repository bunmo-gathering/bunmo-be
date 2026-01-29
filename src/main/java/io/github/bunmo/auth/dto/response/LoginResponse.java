package io.github.bunmo.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "로그인 응답", description = "로그인 응답 정보")
public record LoginResponse(
        @Schema(description = "액세스 토큰", example = "eyJhbGci...")
        String accessToken,
        @Schema(description = "리프레시 토큰", example = "eyJhbGci...")
        String refreshToken,
        @Schema(description = "만료시간(초)", example = "86400")
        Long expiresIn,
        @Schema(description = "새로운 사용자 여부 (true일 경우 온보딩 화면으로 이동)", example = "true")
        boolean isNewMember
) {
}
