package io.github.bunmo.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record KakaoUserInfoResponse(
        @JsonProperty("id")
        Long id,
        @JsonProperty("has_signed_up")
        Boolean hasSignedUp,
        @JsonProperty("connected_at")
        Instant connectedAt,
        @JsonProperty("synched_at")
        Instant synchedAt,
        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount,
        @JsonProperty("for_partner")
        Partner partner
) {
    public record KakaoAccount(
            @JsonProperty("profile")
            Profile profile
    ) {
        public record Profile(
                @JsonProperty("nickname")
                String nickName,
                @JsonProperty("is_default_nickname")
                Boolean isDefaultNickName
        ){ }
    }

    public record Partner(
            @JsonProperty("uuid")
            String uuid
    ) {}
}
