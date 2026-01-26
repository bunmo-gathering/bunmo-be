package io.github.bunmo.auth.controller;

import io.github.bunmo.auth.dto.AuthResultCode;
import io.github.bunmo.auth.dto.request.KakaoLoginRequest;
import io.github.bunmo.auth.dto.response.TokenResponse;
import io.github.bunmo.auth.service.AuthService;
import io.github.bunmo.common.web.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "인증 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "카카오 로그인", description = "카카오 인가 코드를 통해 로그인 또는 회원가입을 진행합니다.")
    @PostMapping("/oauth2/kakao")
    public ResponseEntity<ApiResponse<TokenResponse>> kakaoLogin(
            @Valid @RequestBody KakaoLoginRequest request
    ) {
        TokenResponse response = authService.kakaoLogin(request);
        AuthResultCode resultCode = response.isNewMember()
                ? AuthResultCode.SIGNUP_SUCCESS
                : AuthResultCode.LOGIN_SUCCESS;
        return ResponseEntity.status(resultCode.statusCode())
                .body(ApiResponse.success(resultCode, response));
    }
}
