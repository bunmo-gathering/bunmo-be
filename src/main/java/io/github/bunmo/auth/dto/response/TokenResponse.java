package io.github.bunmo.auth.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        Long expiresIn,
        boolean isNewMember
) {
}
