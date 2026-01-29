package io.github.bunmo.auth.infrastructure.repository;

import io.github.bunmo.auth.infrastructure.domain.SocialAccount;
import io.github.bunmo.auth.infrastructure.domain.enums.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
    Optional<SocialAccount> findByProviderAndProviderId(Provider provider, String providerId);

    String provider(Provider provider);
}
