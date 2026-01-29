package io.github.bunmo.security.service;

import io.github.bunmo.member.infrastructure.domain.Member;
import io.github.bunmo.member.infrastructure.repository.MemberRepository;
import io.github.bunmo.security.CustomUserDetails;
import io.github.bunmo.security.exception.AuthErrorCode;
import io.github.bunmo.security.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new AuthException(AuthErrorCode.ACCESS_DENIED));

        return CustomUserDetails.from(member);
    }
}
