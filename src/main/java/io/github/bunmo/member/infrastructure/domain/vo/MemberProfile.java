package io.github.bunmo.member.infrastructure.domain.vo;

import io.github.bunmo.member.infrastructure.domain.enums.Gender;
import jakarta.persistence.Embeddable;

@Embeddable
public record MemberProfile(
        String nickname,
        Gender gender,
        String profileImageUrl
) {
}
