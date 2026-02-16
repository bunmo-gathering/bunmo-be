package io.github.bunmo.auth.controller.doc;

import io.github.bunmo.auth.dto.request.KakaoLoginRequest;
import io.github.bunmo.auth.dto.request.TokenReissueRequest;
import io.github.bunmo.auth.dto.response.LoginResponse;
import io.github.bunmo.auth.dto.response.TokenResponse;
import io.github.bunmo.common.web.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Auth", description = "인증 API")
public interface AuthControllerDoc {
    @Operation(
            summary = "카카오 로그인",
            description = "카카오 인가 코드를 통해 로그인 또는 회원가입을 진행합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "로그인 성공",
                            content = @Content(
                                    schema = @Schema(implementation = LoginResponse.class),
                                    examples = @ExampleObject(
                                            name = "LOGIN_SUCCESS",
                                            value = """
                                                    {
                                                      "code": "AUTH_001",
                                                      "message": "로그인 성공"
                                                      "data": {
                                                        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2Iiwicm9sZXMiOiJST0xFX01FTUJFUiIsImlhdCI6MTc2OTY2OTAyMiwiZXhwIjoxNzY5NzU1NDIyfQ.vB-SoqbprkSx029Uf6DPrD9RDGr6YNsUPj1feVbLxJGiE4QiVWLIMzc1hd0E3uDpQM9JggdD6kUDkG0BcADNpQ",
                                                        "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2Iiwicm9sZXMiOiJST0xFX01FTUJFUiIsImlhdCI6MTc2OTY2OTAyMiwiZXhwIjoxNzY5OTI4MjIyfQ.l2uMkcOLelVhKr2N2pjhNOo7AdH3uMnKZwbEsrtgVU4GYJQJ4IElGAbfiSZ5E71aOgfnCG9Ds46mN2JywxIPtw",
                                                        "expiresIn": 86400,
                                                        "isNewMember": false
                                                        }
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "회원가입 성공",
                            content = @Content(
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            name = "SIGNUP_SUCCESS",
                                            value = """
                                                    {
                                                      "code": "AUTH_002",
                                                      "message": "회원가입 성공",
                                                      "data": {
                                                        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2Iiwicm9sZXMiOiJST0xFX01FTUJFUiIsImlhdCI6MTc2OTY2OTAyMiwiZXhwIjoxNzY5NzU1NDIyfQ.vB-SoqbprkSx029Uf6DPrD9RDGr6YNsUPj1feVbLxJGiE4QiVWLIMzc1hd0E3uDpQM9JggdD6kUDkG0BcADNpQ",
                                                        "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2Iiwicm9sZXMiOiJST0xFX01FTUJFUiIsImlhdCI6MTc2OTY2OTAyMiwiZXhwIjoxNzY5OTI4MjIyfQ.l2uMkcOLelVhKr2N2pjhNOo7AdH3uMnKZwbEsrtgVU4GYJQJ4IElGAbfiSZ5E71aOgfnCG9Ds46mN2JywxIPtw",
                                                        "expiresIn": 86400,
                                                        "isNewMember": true
                                                      }
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "카카오 인증 요청 시 유효하지 않은 파라미터",
                            content = @Content(
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            name = "KAKAO_INVALID_PARAMETER",
                                            value = """
                                                    {
                                                      "code": "OAUTH_003",
                                                      "message": "유효하지 않은 파라미터에 의해 카카오인증에 실패했습니다"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "유효하지 않은 사용자(탈퇴, 정지 등)",
                            content = @Content(
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            name = "INVALID_MEMBER",
                                            value = """
                                                    {
                                                      "code": "OAUTH_002",
                                                      "message": "유효하지 않은 사용자 입니다"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "존재하지 않는 회원",
                            content = @Content(
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            name = "MEMBER_NOT_FOUND",
                                            value = """
                                                    {
                                                      "code": "MEMBER_001",
                                                      "message": "존재하지 않는 회원입니다"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "카카오 서버 통신 실패",
                            content = @Content(
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            name = "KAKAO_SERVER_ERROR",
                                            value = """
                                                    {
                                                      "code": "OAUTH_004",
                                                      "message": "카카오 서버 에러입니다"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "502",
                            description = "카카오 사용자 정보 요청 실패",
                            content = @Content(
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            name = "KAKAO_USER_INFO_REQUEST_FAILED",
                                            value = """
                                                    {
                                                      "code": "OAUTH_001",
                                                      "message": "카카오 사용자 정보 요청에 실패했습니다"
                                                    }
                                                    """
                                    )
                            )
                    ),
            }
    )
    ResponseEntity<ApiResponse<LoginResponse>> kakaoLogin(
            KakaoLoginRequest request
    );

    @Operation(
        summary = "jwt token 재발급",
        description = "refresh token으로 jwt token을 재발급 합니다",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "토큰 재발급 성공",
                content = @Content(
                    schema = @Schema(implementation = TokenResponse.class),
                    examples = @ExampleObject(
                        name = "LOGIN_SUCCESS",
                        value = """
                            {
                              "code": "AUTH_003",
                              "message": "토큰 재발급 성공",
                              "data": {
                                "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3YWIyZTk2NC0wNDZkLTQxNWMtOWQxNi0yMGNlNWI3ODE0NGUiLCJyb2xlcyI6IlJPTEVfTUVNQkVSIiwiaWF0IjoxNzcxMjMyMDk1LCJleHAiOjE3NzEzMTg0OTV9.fPbNmAZX1kBqGYSj9FxAFGjp8BN5gul0D9sjQMHxV3lp1q_JGeqLnA8u1oWCJi6NqeUT4AMziQtdRWwOiwMEbg",
                                "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3YWIyZTk2NC0wNDZkLTQxNWMtOWQxNi0yMGNlNWI3ODE0NGUiLCJyb2xlcyI6IlJPTEVfTUVNQkVSIiwiaWF0IjoxNzcxMjMyMDk1LCJleHAiOjE3NzE0OTEyOTV9.AsHVQsQFs84DUYAOH4GZs61CuBa0qq7_1sConU7sMYC5DF6RWuLlXSkv0_NNWQC9qaZTf3gNCA_Q-JvU7tNbaQ"
                              }
                            }
                                                    """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "401",
                description = "유효하지 않은 jwt token",
                content = @Content(
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(
                        name = "INVALID_JWT_TOKEN",
                        value = """
                                                    {
                                                      "code": "UNAUTHORIZED_001",
                                                      "message": "유효하지 않은 토큰입니다"
                                                    }
                                                    """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "존재하지 않는 회원",
                content = @Content(
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(
                        name = "MEMBER_NOT_FOUND",
                        value = """
                                                    {
                                                      "code": "MEMBER_001",
                                                      "message": "존재하지 않는 회원입니다"
                                                    }
                                                    """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "403",
                description = "유효하지 않은 회원",
                content = @Content(
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(
                        name = "MEMBER_INVALID_STATUS",
                        value = """
                                                    {
                                                      "code": "MEMBER_002",
                                                      "message": "유효하지 않은 회원 상태입니다"
                                                    }
                                                    """
                    )
                )
            ),
        }
    )
    ResponseEntity<ApiResponse<TokenResponse>> reissueToken(
        TokenReissueRequest request
    );
}
