package io.github.bunmo.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

public record KakaoTokenResponse(
        @JsonProperty("token_type")
        String tokenType,
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("id_token")
        String idToken,
        @JsonProperty("expires_in")
        Integer expiresIn,
        @JsonProperty("refresh_token")
        String refreshToken,
        @JsonProperty("refresh_token_expires_in")
        Integer refreshTokenExpiresIn,
        @JsonProperty("scope")
        String scope
) {
}
