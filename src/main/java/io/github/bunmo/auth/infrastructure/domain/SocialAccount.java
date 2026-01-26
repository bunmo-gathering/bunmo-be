package io.github.bunmo.auth.infrastructure.domain;

import io.github.bunmo.auth.infrastructure.domain.enums.Provider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "social_account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    private SocialAccount(Long memberId, Provider provider, String providerId) {
        this.memberId = memberId;
        this.provider = provider;
        this.providerId = providerId;
    }

    public static SocialAccount create(Long memberId, Provider provider, String providerId) {
        return new SocialAccount(memberId, provider, providerId);
    }
}
