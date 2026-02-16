package io.github.bunmo.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "엑세스 토큰 재발급 응답", description = "엑세스 토큰 응답 정보")
public record TokenResponse(
    @Schema(description = "액세스 토큰", example = "eyJhbGci...")
    String accessToken,
    @Schema(description = "리프레시 토큰", example = "eyJhbGci...")
    String refreshToken
) {

}
