package io.github.bunmo.member;

import jakarta.persistence.Embeddable;

@Embeddable
public record MemberProfile(
        String nickname,
        Gender gender,
        String profileImageUrl
) {
}
