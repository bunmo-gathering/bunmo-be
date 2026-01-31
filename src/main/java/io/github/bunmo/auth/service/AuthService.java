package io.github.bunmo.auth.service;

import io.github.bunmo.auth.dto.request.KakaoLoginRequest;
import io.github.bunmo.auth.dto.response.KakaoTokenResponse;
import io.github.bunmo.auth.dto.response.KakaoUserInfoResponse;
import io.github.bunmo.auth.dto.response.LoginResponse;
import io.github.bunmo.auth.exception.OAuthErrorCode;
import io.github.bunmo.auth.infrastructure.domain.SocialAccount;
import io.github.bunmo.auth.infrastructure.domain.enums.Provider;
import io.github.bunmo.auth.infrastructure.repository.SocialAccountRepository;
import io.github.bunmo.common.exception.BusinessException;
import io.github.bunmo.member.exception.MemberErrorCode;
import io.github.bunmo.member.infrastructure.domain.Member;
import io.github.bunmo.member.infrastructure.repository.MemberRepository;
import io.github.bunmo.security.CustomUserDetails;
import io.github.bunmo.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final KakaoOAuthService kakaoOAuthService;
    private final MemberRepository memberRepository;
    private final SocialAccountRepository socialAccountRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public LoginResponse kakaoLogin(KakaoLoginRequest request) {
        KakaoTokenResponse kakaoToken = kakaoOAuthService.getAccessTokenFromKakao(request.code());
        KakaoUserInfoResponse userInfo = kakaoOAuthService.getUserInfo(kakaoToken.accessToken());
        Long providerId = extractProviderId(userInfo);

        Member member = findOrCreateMember(Provider.KAKAO, providerId);

        Authentication auth = createAuthentication(member);
        return new LoginResponse(
                jwtUtil.createAccessToken(auth),
                jwtUtil.createRefreshToken(auth),
                jwtUtil.getAccessTokenValidity(),
                member.isNewMember()
        );
    }

    private Member findOrCreateMember(Provider provider, Long providerId) {
        return socialAccountRepository.findByProviderAndProviderId(provider, providerId.toString())
                .map(this::findAndValidateMember)
                .orElseGet(() -> createNewMemberWithSocialAccount(provider, providerId));
    }

    private Member findAndValidateMember(SocialAccount socialAccount) {
        Member member = memberRepository.findById(socialAccount.getMemberId())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (!member.validateMemberStatus()) {
            throw new BusinessException(OAuthErrorCode.INVALID_MEMBER);
        }

        return member;
    }

    private Member createNewMemberWithSocialAccount(Provider provider, Long providerId) {
        Member member = Member.createPendingMember();
        memberRepository.save(member);

        SocialAccount socialAccount = SocialAccount.create(member.getId(), provider, providerId.toString());
        socialAccountRepository.save(socialAccount);
        return member;
    }

    private Long extractProviderId(KakaoUserInfoResponse userInfo) {
        if (userInfo.id() == null) {
            throw new BusinessException(OAuthErrorCode.KAKAO_USER_INFO_REQUEST_FAILED);
        }
        return userInfo.id();
    }

    private Authentication createAuthentication(Member member) {
        return new UsernamePasswordAuthenticationToken(
            CustomUserDetails.from(member),
            null,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + member.role().name()))
        );
    }
}
