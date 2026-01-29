package io.github.bunmo.auth.controller;

import io.github.bunmo.auth.controller.doc.AuthControllerDoc;
import io.github.bunmo.auth.dto.AuthResultCode;
import io.github.bunmo.auth.dto.request.KakaoLoginRequest;
import io.github.bunmo.auth.dto.response.LoginResponse;
import io.github.bunmo.auth.service.AuthService;
import io.github.bunmo.common.web.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDoc {
    private final AuthService authService;

    @Override
    @PostMapping("/oauth2/kakao")
    public ResponseEntity<ApiResponse<LoginResponse>> kakaoLogin(
            @Valid @RequestBody KakaoLoginRequest request
    ) {
        LoginResponse response = authService.kakaoLogin(request);
        AuthResultCode resultCode = response.isNewMember()
                ? AuthResultCode.SIGNUP_SUCCESS
                : AuthResultCode.LOGIN_SUCCESS;
        return ResponseEntity.status(resultCode.statusCode())
                .body(ApiResponse.success(resultCode, response));
    }
}
