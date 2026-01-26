package io.github.bunmo.auth.service;

import io.github.bunmo.auth.config.KakaoOAuthProperties;
import io.github.bunmo.auth.dto.response.KakaoTokenResponse;
import io.github.bunmo.auth.dto.response.KakaoUserInfoResponse;
import io.github.bunmo.auth.exception.OAuthErrorCode;
import io.github.bunmo.common.exception.BusinessException;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOAuthService {
    private final KakaoOAuthProperties properties;

    public KakaoTokenResponse getAccessTokenFromKakao(String code) {

        KakaoTokenResponse kakaoTokenResponseDto = WebClient.create(properties.tokenUri()).post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path(properties.tokenPath())
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", properties.clientId())
                        .queryParam("client_secret", properties.clientSecret())
                        .queryParam("code", code)
                        .build(true))
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new BusinessException(OAuthErrorCode.KAKAO_INVALID_PARAMETER)))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new BusinessException(OAuthErrorCode.KAKAO_SERVER_ERROR)))
                .bodyToMono(KakaoTokenResponse.class)
                .block();
        return kakaoTokenResponseDto;
    }

    public KakaoUserInfoResponse getUserInfo(String accessToken) {

        KakaoUserInfoResponse userInfo = WebClient.create(properties.userInfoUri())
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path(properties.userInfoPath())
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new BusinessException(OAuthErrorCode.KAKAO_INVALID_PARAMETER)))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new BusinessException(OAuthErrorCode.KAKAO_SERVER_ERROR)))
                .bodyToMono(KakaoUserInfoResponse.class)
                .block();

        return userInfo;
    }
}
