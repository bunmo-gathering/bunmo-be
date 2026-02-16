package io.github.bunmo.auth.dto.request;

public record TokenReissueRequest(
    String refreshToken
) {

}
